package pl.mszcz.sales;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/api/sales/offer/{productId}")
    void addToCart(@PathVariable String productId) {
        try {
            sales.addToCart(getCurrentCustomerId(), productId);
        } catch (ProductNotAvailableException e) {
            // https://stackoverflow.com/questions/24292373/spring-boot-rest-controller-how-to-return-different-http-status-codes
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Product ID");
        }
    }

    @DeleteMapping("/api/sales/offer/{productId}")
    void removeFromCart(@PathVariable String productId) {
        // TODO
    }

    private String getCurrentCustomerId() {
        return CUSTOMER_ID;
    }
}
