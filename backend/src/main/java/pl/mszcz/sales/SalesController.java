package pl.mszcz.sales;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.mszcz.sales.cart.Offer;
import pl.mszcz.sales.cart.OfferResponse;
import pl.mszcz.sales.exceptions.*;
import pl.mszcz.sales.purchase.CustomerInfo;
import pl.mszcz.sales.purchase.PaymentData;
import pl.mszcz.sales.purchase.PurchaseRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class SalesController {
    public static final String CUSTOMER_ID = "KUBA";

    private Sales sales;

    SalesController(Sales sales) {
        this.sales = sales;
    }

    @GetMapping("/api/sales/offer")
    OfferResponse getCurrentOffer() {
        Offer offer = sales.getCurrentOffer(getCurrentCustomerId());
        try {
            return new OfferResponse(offer, sales.getOfferChecksum(offer));
        } catch (CantGenerateOfferChecksumException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
    PaymentData createPurchase(@RequestBody PurchaseRequest purchaseRequest, HttpServletRequest request) {
        try {
            String clientIp = getClientIpAddress(request);
            return sales.createPurchase(getCurrentCustomerId(), purchaseRequest.customerInfo(), clientIp, purchaseRequest.checksum());
        } catch (EmptyPurchaseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Offer is empty");
        } catch (CantRegisterPaymentException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't generate payment");
        } catch (InvalidOfferChecksumException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Offer checksum mismatch");
        } catch (CantGenerateOfferChecksumException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // https://stackoverflow.com/questions/1979419/how-to-get-client-ip-address-in-spring-bean

    private final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };

    private String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    private String getCurrentCustomerId() {
        return CUSTOMER_ID;
    }
}
