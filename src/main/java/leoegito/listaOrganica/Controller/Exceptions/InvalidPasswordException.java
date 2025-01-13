package leoegito.listaOrganica.Controller.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;

public class InvalidPasswordException extends BadCredentialsException {

    private final HttpStatus httpStatus;
    public InvalidPasswordException(String msg, HttpStatus httpStatus){
        super(msg);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }

}
