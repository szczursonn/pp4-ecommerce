package pl.mszcz.productcatalog;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCatalogTest {
    @Test
    void itAllowsToArchiveProduct() {
        ProductCatalog catalog = thereIsProductCatalog();
        Long productId = catalog.addProduct("gigaprodukt", BigDecimal.TEN).getId();

        assertEquals(1, catalog.getPublishedProducts().size());
        catalog.setArchive(productId, true);

        assertEquals(0, catalog.getPublishedProducts().size());
        assertTrue(catalog.getProductById(productId).orElseThrow().isArchived());
    }

    @Test
    void itAllowsToPublishProductAndSetImageUrl() {
        ProductCatalog catalog = thereIsProductCatalog();
        Long productId = catalog.addProduct("produkcik", BigDecimal.valueOf(12.50)).getId();
        catalog.setImageUrl(productId, "http://fsdfhfds.com/pr1.png");

        List<ProductData> products = catalog.getPublishedProducts();

        assertEquals(1, products.size());
        assertNotNull(products.get(0).getImageUrl());
    }

    private ProductCatalog thereIsProductCatalog() {
        ProductStorage productStorage = new MapProductStorage();
        return new ProductCatalog(productStorage);
    }
}