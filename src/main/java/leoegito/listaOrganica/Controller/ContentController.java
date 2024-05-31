package leoegito.listaOrganica.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Controller
@RequestMapping("/")
public class ContentController {

    @GetMapping("/home")
    public String handleWelcome(){
        return "home";
    }

    @GetMapping("/admin/home")
    public String handleAdminWelcome(){
        return "home_admin";
    }

    @GetMapping("/user/home")
    public String handleUserWelcome(){
        return "home_user";
    }

//    @GetMapping("/login")
//    public String handleLogins(){
//        return "custom_login";
//    }
}
