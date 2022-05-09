package pl.mszcz.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CollectingProductsTest {

    CartStorage cartStorage;

    @BeforeEach
    void setup() {
        cartStorage = new MapCartStorage();
    }

    @Test
    void initialOfferIsEmpty() {
        String customerId = thereIsCustomer("Kuba");
        Sales sales = thereIsSalesModule();

        Offer currentOffer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.ZERO, currentOffer.getTotal());
    }

    @Test
    void itAllowsToAddProduct() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        String productId = thereIsProduct("Lego set", BigDecimal.TEN);
        Sales sales = thereIsSalesModule();

        sales.addToCart(customerId, productId);
        Offer currentOffer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.TEN, currentOffer.getTotal());
    }

    @Test
    void customersManageTheirOwnCarts() throws ProductNotAvailableException {
        String customerId1 = thereIsCustomer("Kuba");
        String customerId2 = thereIsCustomer("Maciek");
        String productId = thereIsProduct("Lego set", BigDecimal.TEN);
        Sales sales = thereIsSalesModule();

        sales.addToCart(customerId1, productId);
        Offer offer1 = sales.getCurrentOffer(customerId1);
        Offer offer2 = sales.getCurrentOffer(customerId2);


        assertEquals(BigDecimal.TEN, offer1.getTotal());
        assertEquals(BigDecimal.ZERO, offer2.getTotal());
    }

    private Sales thereIsSalesModule() {
        return new Sales(cartStorage, new ProductDetailsProvider());
    }

    private String thereIsCustomer(String customerId) {
        return customerId;
    }

    private String thereIsProduct(String id, BigDecimal price) {
        return id;
    }
}
