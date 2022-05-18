package pl.mszcz.productcatalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JpaProductStorageTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JpaProductStorage productStorage;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DELETE FROM product_data");
    }

    @Test
    void itStoresAndLoadsProduct() {
        ProductData data = thereIsProduct("lego-set-1");

        productStorage.save(data);
        ProductData loaded = productStorage.load(data.getId());

        assertEquals(data.getId(), loaded.getId());
    }

    @Test
    void itLoadsAllPublishedProducts() {

        ProductData data1 = thereIsProduct("lego-set-1");
        ProductData data2 = thereIsProduct("lego-set-2");
        data1.setPublished(true);

        productStorage.save(data1);
        productStorage.save(data2);

        assertEquals(1, productStorage.allPublished().size());
    }

    private ProductData thereIsProduct(String productId) {
        return new ProductData(productId, "product-"+productId);
    }
}
