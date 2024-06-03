package leoegito.listaOrganica.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import leoegito.listaOrganica.Model.ListItem;
//import leoegito.listaOrganica.Model.PK.ListItemPK;
import leoegito.listaOrganica.Model.MyUser;
import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Model.ProductList;
import leoegito.listaOrganica.Repository.ListItemRepository;
import leoegito.listaOrganica.Repository.MyUserRepository;
import leoegito.listaOrganica.Repository.ProductListRepository;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ProductListService {

    @Autowired
    private ProductListRepository productListRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private ProductService productService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductList> findAll() {
        return this.productListRepository.findAll();
    }

    public ProductList findByID(Long id) {
        Optional<ProductList> obj = this.productListRepository.findById(id);
        return obj.get();
//        return obj.orElseThrow(
//                () -> new ResourceNotFoundException(id)
//        );
    }

    public ProductList save(ProductList productList) {
        return this.productListRepository.save(productList);
    }

    public ProductList update(Long id, ProductList productList) {
        try {
            this.productListRepository.getReferenceById(id);
            return this.productListRepository.save(productList);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
    //CHATGPT
    public void delete(Long id) {
        ProductList productList = this.productListRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        // Remover todas as associações ListItem
        for (ListItem listItem : new HashSet<>(productList.getListItems())) {
            productList.getListItems().remove(listItem);
        }

        // Salvar o ProductList para persistir a remoção das associações
        this.productListRepository.saveAndFlush(productList);

        // Remover referências na tabela de junção MY_USER_PRODUCT_LISTS
        List<MyUser> users = myUserRepository.findAll();
        for (MyUser user : users) {
            if (user.getProductLists().contains(productList)) {
                user.getProductLists().remove(productList);
                myUserRepository.save(user);
            }
        }

        // Agora deletar o ProductList
        this.productListRepository.delete(productList);
    }

//    @Transactional - antigo
//    public void delete(Long id){
//        ProductList productList = this.productListRepository.findById(id).orElseThrow(
//                ()-> new ResourceNotFoundException(id)
//        );
//        // Remover todas as associações ListItem
//        for (ListItem listItem : productList.getListItems()) {
//            listItem.getId().setProductList(null);
//            listItem.getId().setProduct(null);
////            listItem.getProductList().remove(productList); // Remover a associação do lado do ListItem
////            listItem.setProductList(null);
//        }
//        productList.getListItems().clear(); // Limpar a coleção do lado do ProductList
//        this.productListRepository.save(productList); // Salvar o ProductList para persistir a remoção das
//        this.productListRepository.delete(productList);
//    }
//    @Transactional
//    public void delete(Long id){
//        ProductList productList = this.productListRepository.findById(id).orElseThrow(
//                ()-> new ResourceNotFoundException(id)
//        );
//        // Remover todas as associações ListItem
//        for (ListItem listItem : productList.getListItems()) {
//            this.entityManager.remove(listItem);
//            productList.getListItems().remove(listItem);
//            listItem.setProductList(null);
//        }
//        this.productListRepository.delete(productList);
//    }

//    @Transactional
//    public void delete(Long id){
//        int numTentativas = 0;
//        int MAX_TENTATIVAS = 5;
//        while(numTentativas < MAX_TENTATIVAS) {
//            try {
//                ProductList productList = this.productListRepository.findById(id).orElseThrow(
//                        ()-> new ResourceNotFoundException(id)
//                );
//                // Remover todas as associações ListItem
//                for (ListItem listItem : productList.getListItems()) {
//                    listItem.setProductList(null);
//                    productList.getListItems().remove(listItem);
//                    this.entityManager.remove(listItem);
//                }
//                this.entityManager.refresh(productList);
//                this.productListRepository.delete(productList);
//                break; // Se a operação foi bem-sucedida, saia do loop
//            } catch (ObjectOptimisticLockingFailureException e) {
//                numTentativas++; // Incrementa o contador de tentativas
//                if (numTentativas >= MAX_TENTATIVAS) {
//                    throw e; // Se excedeu o número máximo de tentativas, relance a exceção
//                }
//            }
//        }
//    }

    public Optional<ProductList> insertProduct(Long id, Product product) throws ResourceNotFoundException {
        Optional<ProductList> optionalProductList = productListRepository.findById(id);

        Product realProduct = this.productService.findByID(product.getId());

        if(optionalProductList.isPresent()){
            ProductList productList = optionalProductList.get();
            ListItem listItem = new ListItem(productList, realProduct, 1, realProduct.getMedian());

            // Verificar se o item já existe na lista
            boolean itemExists = productList.getListItems().stream()
                    .anyMatch(item -> item.getId().getProduct().getId().equals(realProduct.getId()));

            if(!itemExists){
                this.listItemRepository.save(listItem);
                productList.getListItems().add(listItem);
                this.productListRepository.save(productList);
            }
        } else {
            throw new ResourceNotFoundException(id);
        }
        return optionalProductList;
    }


}
