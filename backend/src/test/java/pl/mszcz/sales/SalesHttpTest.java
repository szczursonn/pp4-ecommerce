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
import pl.mszcz.productcatalog.exceptions.CantPublishProductException;
import pl.mszcz.productcatalog.ProductCatalog;
import pl.mszcz.sales.cart.Offer;
import pl.mszcz.sales.purchase.CustomerInfo;
import pl.mszcz.sales.purchase.PaymentData;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void itAllowsToAddProduct() throws CantPublishProductException {
        Long productId = thereIsProduct("giga lego set", BigDecimal.valueOf(12.00));

        String url1 = String.format("http://localhost:%s/api/sales/offer/%s", port, productId);
        ResponseEntity<Object> response1 = http.postForEntity(url1, null, null);

        assertEquals(HttpStatus.OK, response1.getStatusCode());

        String url2 = String.format("http://localhost:%s/api/sales/offer", port);
        ResponseEntity<Offer> response2 = http.getForEntity(url2, Offer.class);

        Offer offer = response2.getBody();
        assertEquals(0, BigDecimal.valueOf(12.00).compareTo(offer.total()));
        assertEquals(1, offer.size());

    }

    @Test
    void itAllowsToRemoveProduct() throws CantPublishProductException {
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
    void itAllowsToAddProductWithSpecifiedQuantity() throws CantPublishProductException {
        Long productId = thereIsProduct("lego set fajny taki", BigDecimal.valueOf(12.00));

        String url1 = String.format("http://localhost:%s/api/sales/offer/%s?quantity=5", port, productId);
        ResponseEntity<Offer> response1 = http.postForEntity(url1, null, null);

        assertEquals(HttpStatus.OK, response1.getStatusCode());

        String url2 = String.format("http://localhost:%s/api/sales/offer", port, productId);
        ResponseEntity<Offer> response2 = http.getForEntity(url2, Offer.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());

        Offer offer = response2.getBody();
        assertEquals(5, offer.items().get(0).quantity());
    }

    @Test
    void itDisallowsToAddInvalidProduct() {
        String url = String.format("http://localhost:%s/api/sales/offer/%s", port, 10L);
        ResponseEntity<Offer> response = http.postForEntity(url, null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void itAllowsToCreatePurchase() throws CantPublishProductException {
        Long productId = thereIsProduct("lego set 1", BigDecimal.TEN);
        CustomerInfo customerInfo = thereIsCustomerInfo("maciek", "jacula");

        String url1 = String.format("http://localhost:%s/api/sales/offer/%s", port, productId);
        ResponseEntity<Object> response1 = http.postForEntity(url1, null, null);

        assertEquals(HttpStatus.OK, response1.getStatusCode());

        String url2 = String.format("http://localhost:%s/api/sales/purchase", port);
        ResponseEntity<PaymentData> response2 = http.postForEntity(url2, customerInfo, PaymentData.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
    }

    @Test
    void itDisallowsToCreatePurchaseWithEmptyOffer() {
        CustomerInfo customerInfo = thereIsCustomerInfo("maciek", "jacula");

        String url = String.format("http://localhost:%s/api/sales/purchase", port);
        ResponseEntity<PaymentData> response = http.postForEntity(url, customerInfo, PaymentData.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    private Long thereIsProduct(String name, BigDecimal price) throws CantPublishProductException {
        Long productId = productCatalog.addProduct(name).getId();
        productCatalog.setPrice(productId, price);
        productCatalog.setImageUrl(productId, "sdada");
        productCatalog.publishProduct(productId);
        return productId;
    }

    private CustomerInfo thereIsCustomerInfo(String firstName, String lastName) {
        return new CustomerInfo(firstName, lastName, firstName+lastName+"@gmail.com");
    }
}
