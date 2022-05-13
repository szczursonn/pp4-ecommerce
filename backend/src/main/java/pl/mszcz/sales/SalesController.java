package pl.mszcz.sales;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesController {
    public static final String CUSTOMER_ID = "KUBA";

    private Sales sales;

    SalesController(Sales sales) {
        this.sales = sales;
    }

    @GetMapping("/api/sales/offer")
    Offer getCurrentOffer() {
        return sales.getCurrentOffer(getCurrentCustomerId());
    }

    private String getCurrentCustomerId() {
        return CUSTOMER_ID;
    }
}
