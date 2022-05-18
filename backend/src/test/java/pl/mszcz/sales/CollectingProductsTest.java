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
        Long productId = thereIsProduct(5L, BigDecimal.TEN);

        sales.addToCart(customerId, productId, 1);
        Offer currentOffer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.TEN, currentOffer.getTotal());
    }

    @Test
    void itAllowsToGetOfferSize() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        Long productId1 = thereIsProduct(5L, BigDecimal.valueOf(10.00));
        Long productId2 = thereIsProduct(10L, BigDecimal.valueOf(15.00));
        Long productId3 = thereIsProduct(15L, BigDecimal.valueOf(5.00));

        sales.addToCart(customerId, productId1, 2);
        sales.addToCart(customerId, productId2, 4);
        sales.addToCart(customerId, productId3, 7);

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(3, offer.getItems().size());
    }

    @Test
    void itAllowsToSetQuantity() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        Long productId = thereIsProduct(10L, BigDecimal.TEN);

        sales.addToCart(customerId, productId, 5);

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(1, offer.getItems().size());
        assertEquals(5, offer.getItems().get(0).getQuantity());
    }

    @Test
    void itOverwritesPreviousQuantity() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        Long productId = thereIsProduct(10L, BigDecimal.TEN);

        sales.addToCart(customerId, productId, 1);
        sales.addToCart(customerId, productId, 7);

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(1, offer.getItems().size());
        assertEquals(7, offer.getItems().get(0).getQuantity());

    }

    @Test
    void itAllowsToAddDifferentProducts() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        Long productId1 = thereIsProduct(10L, BigDecimal.valueOf(15.00));
        Long productId2 = thereIsProduct(20L, BigDecimal.valueOf(6.00));

        sales.addToCart(customerId, productId1, 1);
        sales.addToCart(customerId, productId2, 1);

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.valueOf(21.00), offer.getTotal());
        assertEquals(2, offer.getItemsCount());

    }

    @Test
    void customersManageTheirOwnCarts() throws ProductNotAvailableException {
        String customerId1 = thereIsCustomer("Kuba");
        String customerId2 = thereIsCustomer("Maciek");
        Long productId = thereIsProduct(123L, BigDecimal.TEN);

        sales.addToCart(customerId1, productId, 1);
        Offer offer1 = sales.getCurrentOffer(customerId1);
        Offer offer2 = sales.getCurrentOffer(customerId2);


        assertEquals(BigDecimal.TEN, offer1.getTotal());
        assertEquals(BigDecimal.ZERO, offer2.getTotal());
    }

    private String thereIsCustomer(String customerId) {
        return customerId;
    }

    private Long thereIsProduct(Long id, BigDecimal price) {

        productDetails.add(
                new ProductDetails(id, "product-"+id, price)
        );

        return id;
    }
}
