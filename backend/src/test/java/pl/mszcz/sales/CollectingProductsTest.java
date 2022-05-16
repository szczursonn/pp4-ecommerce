package pl.mszcz.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CollectingProductsTest {

    CartStorage cartStorage;
    Sales sales;
    ArrayList<ProductDetails> productDetails;

    @BeforeEach
    void setup() {
        cartStorage = new MapCartStorage();
        productDetails = new ArrayList<>();
        sales = new Sales(cartStorage, new ListProductDetailsProvider(productDetails));
    }

    @Test
    void initialOfferIsEmpty() {
        String customerId = thereIsCustomer("Kuba");

        Offer currentOffer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.ZERO, currentOffer.getTotal());
    }

    @Test
    void itAllowsToAddProduct() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        String productId = thereIsProduct("Lego set", BigDecimal.TEN);

        sales.addToCart(customerId, productId);
        Offer currentOffer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.TEN, currentOffer.getTotal());
    }

    @Test
    void itAllowsToAddSameProductTwice() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        String productId = thereIsProduct("lego-set-1", BigDecimal.TEN);

        sales.addToCart(customerId, productId);
        sales.addToCart(customerId, productId);

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(1, offer.getItems().size());
        assertEquals(2, offer.getItems().get(0).getQuantity());

    }

    @Test
    void itAllowsToAddDifferentProducts() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        String productId1 = thereIsProduct("lego-set-1", BigDecimal.valueOf(15.00));
        String productId2 = thereIsProduct("lego-set-2", BigDecimal.valueOf(6.00));

        sales.addToCart(customerId, productId1);
        sales.addToCart(customerId, productId2);

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.valueOf(21.00), offer.getTotal());
        assertEquals(2, offer.getItemsCount());

    }

    @Test
    void customersManageTheirOwnCarts() throws ProductNotAvailableException {
        String customerId1 = thereIsCustomer("Kuba");
        String customerId2 = thereIsCustomer("Maciek");
        String productId = thereIsProduct("Lego set", BigDecimal.TEN);

        sales.addToCart(customerId1, productId);
        Offer offer1 = sales.getCurrentOffer(customerId1);
        Offer offer2 = sales.getCurrentOffer(customerId2);


        assertEquals(BigDecimal.TEN, offer1.getTotal());
        assertEquals(BigDecimal.ZERO, offer2.getTotal());
    }

    private String thereIsCustomer(String customerId) {
        return customerId;
    }

    private String thereIsProduct(String id, BigDecimal price) {

        productDetails.add(
                new ProductDetails(id, "product-"+id, price)
        );

        return id;
    }
}
