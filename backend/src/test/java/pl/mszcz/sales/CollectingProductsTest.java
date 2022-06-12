package pl.mszcz.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mszcz.productcatalog.ProductData;
import pl.mszcz.sales.cart.MD5OfferChecksumGenerator;
import pl.mszcz.sales.cart.MapCartItemStorage;
import pl.mszcz.sales.cart.Offer;
import pl.mszcz.sales.exceptions.ProductNotAvailableException;
import pl.mszcz.sales.purchase.DummyPaymentGateway;
import pl.mszcz.sales.purchase.MapPurchaseStorage;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CollectingProductsTest {

    Sales sales;
    ArrayList<ProductData> productDetails;

    @BeforeEach
    void setup() {
        productDetails = new ArrayList<>();
        sales = new Sales(
                new ListProductDetailsProvider(productDetails),
                new MapCartItemStorage(),
                new MapPurchaseStorage(),
                new DummyPaymentGateway(),
                new MD5OfferChecksumGenerator()
        );
    }

    @Test
    void initialOfferIsEmpty() {
        String customerId = thereIsCustomer("Kuba");

        Offer currentOffer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.ZERO, currentOffer.total());
    }

    @Test
    void itAllowsToAddProduct() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        Long productId = thereIsProduct(5L, BigDecimal.TEN);

        sales.addToCart(customerId, productId, 1);
        Offer currentOffer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.TEN, currentOffer.total());
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

        assertEquals(3, offer.size());
    }

    @Test
    void itAllowsToSetQuantity() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        Long productId = thereIsProduct(10L, BigDecimal.TEN);

        sales.addToCart(customerId, productId, 5);

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(1, offer.size());
        assertEquals(5, offer.items().get(0).quantity());
    }

    @Test
    void itOverwritesPreviousQuantity() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        Long productId = thereIsProduct(10L, BigDecimal.TEN);

        sales.addToCart(customerId, productId, 1);
        sales.addToCart(customerId, productId, 7);

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(1, offer.size());
        assertEquals(7, offer.items().get(0).quantity());

    }

    @Test
    void itAllowsToAddDifferentProducts() throws ProductNotAvailableException {
        String customerId = thereIsCustomer("Kuba");
        Long productId1 = thereIsProduct(10L, BigDecimal.valueOf(15.00));
        Long productId2 = thereIsProduct(20L, BigDecimal.valueOf(6.00));

        sales.addToCart(customerId, productId1, 1);
        sales.addToCart(customerId, productId2, 1);

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(BigDecimal.valueOf(21.00), offer.total());
        assertEquals(2, offer.size());

    }

    @Test
    void customersManageTheirOwnCarts() throws ProductNotAvailableException {
        String customerId1 = thereIsCustomer("Kuba");
        String customerId2 = thereIsCustomer("Maciek");
        Long productId = thereIsProduct(123L, BigDecimal.TEN);

        sales.addToCart(customerId1, productId, 1);
        Offer offer1 = sales.getCurrentOffer(customerId1);
        Offer offer2 = sales.getCurrentOffer(customerId2);


        assertEquals(BigDecimal.TEN, offer1.total());
        assertEquals(BigDecimal.ZERO, offer2.total());
    }

    private String thereIsCustomer(String customerId) {
        return customerId;
    }

    private Long thereIsProduct(Long id, BigDecimal price) {

        ProductData product = new ProductData(id, "product-"+id, price);

        productDetails.add(product);

        return id;
    }
}
