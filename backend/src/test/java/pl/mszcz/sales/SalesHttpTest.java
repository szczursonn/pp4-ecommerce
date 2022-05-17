package pl.mszcz.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.mszcz.productcatalog.ProductCatalog;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalesHttpTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate http;

    @Autowired
    ProductCatalog productCatalog;

    @BeforeEach
    void setup() {
        // TODO: reset product catalog between tests
    }

    @Test
    void itLoadsCurrentOffer() {
        String url = String.format("http://localhost:%s/api/sales/offer", port);
        ResponseEntity<Offer> response = http.getForEntity(url, Offer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void itAllowsToAddProduct() {
        String productId = thereIsProduct("super-lego-set-12", BigDecimal.valueOf(12.00));

        String url = String.format("http://localhost:%s/api/sales/offer/%s", port, productId);
        ResponseEntity<Offer> response = http.postForEntity(url, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void itAllowsToAddProductWithSpecifiedQuantity() {
        String productId = thereIsProduct("super-lego-set-20", BigDecimal.valueOf(12.00));

        String url1 = String.format("http://localhost:%s/api/sales/offer/%s?quantity=5", port, productId);
        ResponseEntity<Offer> response1 = http.postForEntity(url1, null, null);

        assertEquals(HttpStatus.OK, response1.getStatusCode());

        String url2 = String.format("http://localhost:%s/api/sales/offer", port, productId);
        ResponseEntity<Offer> response2 = http.getForEntity(url2, Offer.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());

        Offer offer = response2.getBody();
        assertEquals(5, offer.getItems().get(0).getQuantity());
    }

    @Test
    void itDisallowsToAddInvalidProduct() {
        String url = String.format("http://localhost:%s/api/sales/offer/%s", port, "super-lego-set-12");
        ResponseEntity<Offer> response = http.postForEntity(url, null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private String thereIsProduct(String productId, BigDecimal price) {
        productCatalog.addProduct(productId, "product-"+productId);
        productCatalog.setPrice(productId, price);
        return productId;
    }
}
