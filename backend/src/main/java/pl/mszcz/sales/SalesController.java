package pl.mszcz.sales;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.mszcz.sales.cart.Offer;
import pl.mszcz.sales.exceptions.EmptyPurchaseException;
import pl.mszcz.sales.exceptions.ProductNotAvailableException;
import pl.mszcz.sales.purchase.CustomerInfo;
import pl.mszcz.sales.purchase.PaymentData;

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

    @PostMapping("/api/sales/purchase") // Returns a payment url
    PaymentData createPurchase(@RequestBody CustomerInfo customerInfo) {
        try {
            PaymentData paymentData = sales.createPurchase(getCurrentCustomerId(), customerInfo);
            return paymentData;
        } catch (EmptyPurchaseException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Offer is empty");
        }
    }

    private String getCurrentCustomerId() {
        return CUSTOMER_ID;
    }
}
