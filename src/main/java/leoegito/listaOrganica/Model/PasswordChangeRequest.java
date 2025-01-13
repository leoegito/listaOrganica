package leoegito.listaOrganica.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordChangeRequest {

    private String oldPassword;
    private String newPassword;

}
