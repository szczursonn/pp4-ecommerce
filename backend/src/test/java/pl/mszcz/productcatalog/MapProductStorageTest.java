package pl.mszcz.productcatalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapProductStorageTest {
    @Test
    void itAllowsToStoreAndLoadProduct() {
        ProductData product = thereIsExampleProduct();
        ProductStorage listProductStorage = thereIsListProductStorage();

        listProductStorage.save(product);
        ProductData loaded = listProductStorage.load(product.getId())
                .orElseThrow();

        assertEquals(product.getId(), loaded.getId());
        assertEquals(product.getName(), loaded.getName());
    }

    private ProductStorage thereIsListProductStorage() {
        return new MapProductStorage();
    }

    private ProductData thereIsExampleProduct() {
        return new ProductData(1L, "Nice One");
    }
}
