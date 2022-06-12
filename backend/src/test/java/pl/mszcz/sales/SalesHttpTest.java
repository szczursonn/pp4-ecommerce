package pl.mszcz.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.mszcz.productcatalog.ProductCatalog;
import pl.mszcz.sales.cart.Offer;
import pl.mszcz.sales.cart.OfferResponse;
import pl.mszcz.sales.purchase.CustomerInfo;
import pl.mszcz.sales.purchase.PaymentData;
import pl.mszcz.sales.purchase.PurchaseRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalesHttpTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate http;

    @Autowired
    ProductCatalog productCatalog;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DELETE FROM purchase_item");
        jdbcTemplate.execute("DELETE FROM purchase");
        jdbcTemplate.execute("DELETE FROM cart_item");
        jdbcTemplate.execute("DELETE FROM product_data");
    }

    @Test
    void itLoadsCurrentOffer() {
        String url = String.format("http://localhost:%s/api/sales/offer", port);
        ResponseEntity<Offer> response = http.getForEntity(url, Offer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void itAllowsToAddProduct() {
        Long productId = thereIsProduct("giga lego set", BigDecimal.valueOf(12.00));

        String url1 = String.format("http://localhost:%s/api/sales/offer/%s", port, productId);
        ResponseEntity<Object> response1 = http.postForEntity(url1, null, null);

        assertEquals(HttpStatus.OK, response1.getStatusCode());

        String url2 = String.format("http://localhost:%s/api/sales/offer", port);
        ResponseEntity<OfferResponse> response2 = http.getForEntity(url2, OfferResponse.class);

        Offer offer = response2.getBody().offer();
        assertEquals(0, BigDecimal.valueOf(12.00).compareTo(offer.total()));
        assertEquals(1, offer.size());

    }

    @Test
    void itAllowsToRemoveProduct() {
        Long productId = thereIsProduct("fantastyczny secik lego", BigDecimal.valueOf(17.20));

        String url = String.format("http://localhost:%s/api/sales/offer/%s", port, productId);
        ResponseEntity<Offer> response1 = http.postForEntity(url, null, null);

        assertEquals(HttpStatus.OK, response1.getStatusCode());

        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<Void> response2 = http.exchange(url, HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());

        ResponseEntity<Offer> response3 = http.getForEntity(url, Offer.class);
        Offer offer = response3.getBody();

        assertEquals(0, offer.size());
    }

    @Test
    void itDisallowsToRemoveProductThatIsNotInCart() {

        String url = String.format("http://localhost:%s/api/sales/offer/%s", port, 54395439L);

        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<Void> response = http.exchange(url, HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void itAllowsToAddProductWithSpecifiedQuantity() {
        Long productId = thereIsProduct("lego set fajny taki", BigDecimal.valueOf(12.00));

        String url1 = String.format("http://localhost:%s/api/sales/offer/%s?quantity=5", port, productId);
        ResponseEntity<Object> response1 = http.postForEntity(url1, null, null);

        assertEquals(HttpStatus.OK, response1.getStatusCode());


        String url2 = String.format("http://localhost:%s/api/sales/offer", port);
        ResponseEntity<OfferResponse> response2 = http.getForEntity(url2, OfferResponse.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());

        Offer offer = response2.getBody().offer();
        assertEquals(5, offer.items().get(0).quantity());
    }

    @Test
    void itDisallowsToAddInvalidProduct() {
        String url = String.format("http://localhost:%s/api/sales/offer/%s", port, 10L);
        ResponseEntity<Offer> response = http.postForEntity(url, null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void itAllowsToCreatePurchase() {
        Long productId = thereIsProduct("lego set 1", BigDecimal.TEN);
        CustomerInfo customerInfo = thereIsCustomerInfo("maciek", "jacula");

        // add product to cart
        String url1 = String.format("http://localhost:%s/api/sales/offer/%s", port, productId);
        ResponseEntity<Object> response1 = http.postForEntity(url1, null, null);

        assertEquals(HttpStatus.OK, response1.getStatusCode());

        // get cart
        String url2 = String.format("http://localhost:%s/api/sales/offer", port);
        ResponseEntity<OfferResponse> response2 = http.getForEntity(url2, OfferResponse.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());

        String checksum = response2.getBody().checksum();

        PurchaseRequest purchaseRequest = new PurchaseRequest(customerInfo, checksum);

        // make purchase
        String url3 = String.format("http://localhost:%s/api/sales/purchase", port);
        ResponseEntity<PaymentData> response3 = http.postForEntity(url3, purchaseRequest, PaymentData.class);

        assertEquals(HttpStatus.OK, response3.getStatusCode());
        assertNotNull(response3.getBody());
    }

    @Test
    void itDisallowsToCreatePurchaseWithWrongChecksum() {
        Long productId = thereIsProduct("lego set 1", BigDecimal.TEN);
        CustomerInfo customerInfo = thereIsCustomerInfo("maciek", "jacula");

        // add product to cart
        String url1 = String.format("http://localhost:%s/api/sales/offer/%s", port, productId);
        ResponseEntity<Object> response1 = http.postForEntity(url1, null, null);

        assertEquals(HttpStatus.OK, response1.getStatusCode());

        PurchaseRequest purchaseRequest = new PurchaseRequest(customerInfo, "SDFGHDSGHFJSDJGHFSDGHJFJHDSFJHG123");

        // make purchase
        String url2 = String.format("http://localhost:%s/api/sales/purchase", port);
        ResponseEntity<PaymentData> response2 = http.postForEntity(url2, purchaseRequest, PaymentData.class);

        assertEquals(HttpStatus.CONFLICT, response2.getStatusCode());
    }

    @Test
    void itDisallowsToCreatePurchaseWithEmptyOffer() {
        CustomerInfo customerInfo = thereIsCustomerInfo("maciek", "jacula");

        String url = String.format("http://localhost:%s/api/sales/purchase", port);
        ResponseEntity<PaymentData> response = http.postForEntity(url, customerInfo, PaymentData.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void itEmptiesTheCartAfterPurchase() {
        Long productId = thereIsProduct("lego set 1", BigDecimal.TEN);
        CustomerInfo customerInfo = thereIsCustomerInfo("maciek", "jacula");

        // add product to cart
        String url1 = String.format("http://localhost:%s/api/sales/offer/%s", port, productId);
        ResponseEntity<Object> response1 = http.postForEntity(url1, null, null);

        // get checksum
        String url2 = String.format("http://localhost:%s/api/sales/offer", port);
        ResponseEntity<OfferResponse> response2 = http.getForEntity(url2, OfferResponse.class);
        String checksum = response2.getBody().checksum();

        // create purchase
        String url3 = String.format("http://localhost:%s/api/sales/purchase", port);
        PurchaseRequest purchaseRequest = new PurchaseRequest(customerInfo, checksum);
        ResponseEntity<PaymentData> response3 = http.postForEntity(url3, purchaseRequest, PaymentData.class);

        assertEquals(response3.getStatusCode(), HttpStatus.OK);

        // get cart
        String url4 = String.format("http://localhost:%s/api/sales/offer", port);
        ResponseEntity<OfferResponse> response4 = http.getForEntity(url4, OfferResponse.class);

        Offer offer = response4.getBody().offer();

        assertEquals(0, offer.size());
        assertTrue(offer.items().isEmpty());
    }

    @Test
    void itUpdatesOfferChecksum() {
        Long productId1 = thereIsProduct("lego set 1", BigDecimal.TEN);
        Long productId2 = thereIsProduct("lego set 3434343", BigDecimal.valueOf(123.53));
        CustomerInfo customerInfo = thereIsCustomerInfo("maciek", "jacula");

        String cartUrl = String.format("http://localhost:%s/api/sales/offer", port);

        // add product to cart
        String url1 = String.format("http://localhost:%s/api/sales/offer/%s", port, productId1);
        http.postForEntity(url1, null, null);

        // get checksum
        String checksum1 = http.getForObject(cartUrl, OfferResponse.class).checksum();

        // change product quantity
        String url2 = String.format("http://localhost:%s/api/sales/offer/%s?quantity=10", port, productId1);
        http.postForEntity(url2, null, null);

        // get checksum
        String checksum2 = http.getForObject(cartUrl, OfferResponse.class).checksum();

        // change product quantity
        String url3 = String.format("http://localhost:%s/api/sales/offer/%s?quantity=5", port, productId2);
        http.postForEntity(url3, null, null);

        // get checksum
        String checksum3 = http.getForObject(cartUrl, OfferResponse.class).checksum();

        assertNotEquals(checksum1, checksum2);
        assertNotEquals(checksum2, checksum3);
        assertNotEquals(checksum1, checksum3);

    }

    private Long thereIsProduct(String name, BigDecimal price) {
        Long productId = productCatalog.addProduct(name, price).getId();
        productCatalog.setImageUrl(productId, "sdada");
        return productId;
    }

    private CustomerInfo thereIsCustomerInfo(String firstName, String lastName) {
        return new CustomerInfo(firstName, lastName, firstName+lastName+"@gmail.com");
    }
}
