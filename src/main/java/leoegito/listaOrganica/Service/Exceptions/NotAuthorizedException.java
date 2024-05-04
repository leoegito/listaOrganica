package leoegito.listaOrganica.Service.Exceptions;

public class NotAuthorizedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NotAuthorizedException(){
        System.out.println("Acesso não autorizado.");
    }

}
