package leoegito.listaOrganica.Model;

import jakarta.persistence.Entity;

@Entity
public class AdminUser extends MyUser {

    private boolean privileges;
    private int loginAttemps;

}
