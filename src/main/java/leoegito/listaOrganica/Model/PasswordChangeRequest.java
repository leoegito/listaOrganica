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

    //Debug
//    @Override
//    public String toString() {
//        return "oldPassword: " +this.getOldPassword() +" newPassword: " +this.getNewPassword();
//    }
}
