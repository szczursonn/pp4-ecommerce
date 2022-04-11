package pl.mszcz.productcatalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JpaProductStorageTest {

    @Autowired
    ProductDataCRUD productDataCRUD;

    @Test
    void itStoresAndLoadsProduct() {
        ProductData data = new ProductData("id-1", "lego set");
        productDataCRUD.save(data);
        ProductData loaded = productDataCRUD.getById(data.getId());
        assertEquals(data.getId(), loaded.getId());
    }
}
