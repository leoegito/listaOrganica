package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

}
