package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;


}
