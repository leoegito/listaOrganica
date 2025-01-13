
# Lista Orgânica

O preço elevado dos produtos orgânicos é um fator determinante para o padrão brasileiro de consumo desses itens. Este projeto foi criado para facilitar a confecção de listas de compras de alimentos orgânicos, com preços preenchidos colaborativamente e atualizados em tempo real. Assim, consumidores pouco familiarizados com o tema podem planejar suas compras de forma acessível.

---

## 🚀 Sobre o Projeto

Este é um projeto back-end desenvolvido em **Java 17**, utilizando **Spring Boot**, **Spring Security** e **Hibernate**, estruturado como uma API RESTful. Ele utiliza o banco de dados **MariaDB**, com suporte a consultas robustas e gestão de usuários e produtos.

O front-end correspondente foi desenvolvido no framework Angular e pode ser encontrado no repositório irmão: [Front-End Lista Orgânica](https://github.com/leoegito/front-end-listaOrganica-Angular).

---

## 📋 Instalação e Funcionamento

Este projeto pode ser utilizado em conjunto com o front-end ou por meio de ferramentas de testes REST, como Postman ou Insomnia.

### **Requisitos**
- JDK 17
- Spring Boot
- Hibernate
- Spring Security
- MariaDB (ou MySQL, opcionalmente)
- IDE de sua preferência (IntelliJ, Eclipse, VS Code)

### **Configuração**
1. Clone este repositório:
   ```bash
   git clone https://github.com/leoegito/listaOrganica.git
   ```
2. Configure o arquivo `application.properties` com os dados do seu ambiente:
   ```properties
   spring.datasource.url=jdbc:mariadb://localhost:3306/listaorganica
   spring.datasource.username=u_api_lista_organica
   spring.datasource.password=senha
   ```
3. Execute o projeto na sua IDE ou com:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🛠️ Endpoints da API

### **Usuários (`/users`)**
Gerenciamento de usuários, permitindo cadastro, login e consulta.

### **Administrador (`/console`)**
Funções exclusivas para administradores, como remoção de preços, edição de usuários e produtos.

### **Listas de Compras (`/shoppingList`)**
Criação e gerenciamento de listas associadas aos usuários.

Uma lista deve ser criada obrigatoriamente por um usuário existente. Portanto, primeiro o usuário deve ser cadastrado, e só então a lista poderá ser criada.

### **Produtos (`/product`)**
Cadastro e consulta de produtos, incluindo pesquisa por nome.

O JSON de retorno deste endpoint é mais indicado para o retorno dos preços por produto (mediana, preço do usuário, maior preço, menor preço), já que são atributos e da classe/entidade Product, que possui relacionamento com a classe/entidade Price.

### **Preços (`/price`)**
Gerenciamento de preços associados a produtos, com validações para evitar discrepâncias.

Controla o cadastro e consulta de preços. Um preço deve obrigatoriamente ser cadastrado em um produto, portanto, no endpoint `/price/product/{product_id}`.

Caso um preço seja 100% maior do que o maior preço cadastro ou 50% do valor do menor preço cadastrdo, ele será descartado - essa regra é essencial para manter a qualidade dos registros. Apenas um usuário administrador pode ignorar essas restrições, através de endpoint específico.

### **Pesquisa (`/product/search=?searchName`)**
Busca de produtos por nome parcial ou completo, com suporte a autocomplete no repositório irmão (front-end).

---

## 🗂️ Entidades e Relacionamentos

A imagem abaixo ilustra os relacionamentos entre as entidades básicas do projeto, que incluem diversos relacionamentos **n:m** (muitos para muitos):

![Modelo ER](path/to/your/image.png)

---

## 🗺️ Roadmap

- Implementar SwaggerUI para documentação de endpoints.
- Melhorias na documentação
- Substituir autenticação HTTP Basic por JWT.
- Adicionar suporte a Login SSO com Google.

---

## 💡 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests para melhorias.

---