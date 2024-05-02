package leoegito.listaOrganica.Model;

import jakarta.persistence.Entity;

@Entity
public class AdminUser extends User{

    private boolean privileges;
    private int loginAttemps;

}
