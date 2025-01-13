package leoegito.listaOrganica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ListaOrganicaApplication {
	//TODO - Paginação
	//TODO - Transferir lógica de negócio inserida em alguns controllers no fim do projeto para os services
	//TODO - Mudar forma de autenticação para tokens JWT (em andamento)
	//TODO - Criar lógica para trasnformar uma lista em um "post de rede social"
		// 1. Curtidas
		// 2. Copiar lista do post para o usuário
		// 3. Comentar post/lista
		// *. Exemplos: Receitas, Lista de Frutas da Estação etc.
	//TODO - Adicionar atributo de localização para um preço
	//TODO - Informar a localização do menor preço para o usuário
	//TODO - Implementar o uso de localização do browser do usuário
	//TODO - Informar a localização do menor preço mais próximo ao usuário
	public static void main(String[] args) {
		SpringApplication.run(ListaOrganicaApplication.class, args);
	}

}
