package pl.mszcz.productcatalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SQLProductStorageTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute(
                "DROP TABLE product_catalog__products IF EXISTS"
        );

        jdbcTemplate.execute("CREATE TABLE product_catalog__products" +
                "(" +
                "id VARCHAR(100) NOT NULL," +
                "name VARCHAR(100) NOT NULL," +
                "PRIMARY KEY(id)" +
                ")");
    }

    @Test
    void example() {
        String now = jdbcTemplate.queryForObject("SELECT now()", String.class);
    }

    @Test
    void itCountsProducts() {
        Integer productsCount = jdbcTemplate
                .queryForObject("select " + "count(*) " + "from " + "product_catalog__products",
                        Integer.class);

        assertEquals(0, productsCount);
    }

    @Test
    void itCountsWhenStorageNotEmpty() {

        jdbcTemplate.execute("INSERT INTO product_catalog__products VALUES ('lego-set-1', 'nice one')");
        jdbcTemplate.execute("INSERT INTO product_catalog__products VALUES ('lego-set-2', 'calkiem fajne')");

        Integer productsCount = jdbcTemplate
                .queryForObject("select " + "count(*) " + "from " + "product_catalog__products",
                        Integer.class);

        assertEquals(2, productsCount);
    }

    private ProductStorage thereIsSQLProductStorage() {
        return new SQLProductStorage(jdbcTemplate);
    }

    private ProductData thereIsExampleProduct() {
        return new ProductData("lego", "Nice One");
    }
}
