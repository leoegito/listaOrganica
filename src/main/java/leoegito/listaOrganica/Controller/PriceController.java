package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Service.PriceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceController {

    private PriceService priceService;

}
