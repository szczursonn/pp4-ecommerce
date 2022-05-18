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
        ProductData data = thereIsProduct(1L);

        productStorage.save(data);
        ProductData loaded = productStorage.load(data.getId())
                .orElseThrow();

        assertEquals(data.getId(), loaded.getId());
    }

    @Test
    void itLoadsAllPublishedProducts() {

        ProductData data1 = thereIsProduct(1L);
        ProductData data2 = thereIsProduct(2L);
        data1.setPublished(true);

        productStorage.save(data1);
        productStorage.save(data2);

        assertEquals(1, productStorage.allPublished().size());
    }

    private ProductData thereIsProduct(Long productId) {
        return productStorage.save(new ProductData(productId, "product-"+productId));
    }
}
