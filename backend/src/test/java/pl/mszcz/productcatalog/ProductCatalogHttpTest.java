package pl.mszcz.productcatalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCatalogHttpTest {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate http;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductCatalog productCatalog;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DELETE FROM product_data");
        productCatalog.addProduct("legoset1", BigDecimal.TEN);
        productCatalog.addProduct("legoset2", BigDecimal.ONE);
    }

    @Test
    void itLoadsPublishedProducts() {
        String url = String.format("http://localhost:%s/api/products", port);
        // https://www.baeldung.com/spring-rest-template-list#2-using-a-wrapper-class
        ResponseEntity<ProductData[]> response = http.getForEntity(url, ProductData[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<ProductData> products = List.of(response.getBody());

        assertEquals(2, products.size());
    }

    @Test
    void itDoesntLoadArchivedProducts() {

        Long productId = productCatalog.getPublishedProducts().get(0).getId();
        productCatalog.setArchive(productId, true);

        String url = String.format("http://localhost:%s/api/products", port);
        // https://www.baeldung.com/spring-rest-template-list#2-using-a-wrapper-class
        ResponseEntity<ProductData[]> response = http.getForEntity(url, ProductData[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<ProductData> products = List.of(response.getBody());

        assertEquals(1, products.size());
    }
}