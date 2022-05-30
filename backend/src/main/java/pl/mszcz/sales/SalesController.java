package pl.mszcz.sales;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
    void addToCart(@PathVariable Long productId, @RequestParam Optional<Integer> quantity) {
        try {
            sales.addToCart(getCurrentCustomerId(), productId, quantity.orElse(1));
        } catch (ProductNotAvailableException e) {
            // https://stackoverflow.com/questions/24292373/spring-boot-rest-controller-how-to-return-different-http-status-codes
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Product ID");
        }
    }

    @DeleteMapping("/api/sales/offer/{productId}")
    void removeFromCart(@PathVariable Long productId) {
        try {
            sales.removeFromCart(getCurrentCustomerId(), productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not in cart");
        }
    }

    @GetMapping("/api/sales/purchase")
    Purchase getCurrentPurchase() {
        return sales.createPurchase(getCurrentCustomerId());
    }

    private String getCurrentCustomerId() {
        return CUSTOMER_ID;
    }
}
