package pl.mszcz.productcatalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLProductStorageTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void example() {
        jdbcTemplate.execute("select version()");
    }

    @Test
    void itAllowsToStoreAndLoadProduct() {
        ProductData product = thereIsExampleProduct();
        ProductStorage listProductStorage = thereIsListProductStorage();

        listProductStorage.save(product);
        ProductData loaded = listProductStorage.load(product.getId());

        assertEquals(product.getId(), loaded.getId());
        assertEquals(product.getName(), loaded.getName());
    }

    private ProductStorage thereIsListProductStorage() {
        return new SQLProductStorage(jdbcTemplate);
    }

    private ProductData thereIsExampleProduct() {
        return new ProductData("lego", "Nice One");
    }
}
