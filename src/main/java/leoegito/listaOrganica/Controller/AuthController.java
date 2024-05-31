package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.DTO.CredentialsDto;
import leoegito.listaOrganica.DTO.UserDto;
import leoegito.listaOrganica.Model.MyUser;
import leoegito.listaOrganica.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AuthController {

    @Autowired
    MyUserService userService;

    @PostMapping("/loginJWT")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto user = userService.login(credentialsDto);
        return ResponseEntity.ok(user);
    }

}
