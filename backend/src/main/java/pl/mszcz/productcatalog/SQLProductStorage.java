package pl.mszcz.productcatalog;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SQLProductStorage implements ProductStorage {
    private final JdbcTemplate jdbcTemplate;

    public SQLProductStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(ProductData newProduct) {

    }

    @Override
    public ProductData load(String productId) {
        return null;
    }

    @Override
    public List<ProductData> allPublished() {
        return null;
    }
}
