package pl.mszcz.productcatalog;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductCatalogTest {
    @Test
    void itAllowsToListOnlyPublishedProducts() {
        ProductCatalog catalog = thereIsProductCatalog();
        List<ProductData> products = catalog.getPublishedProducts();
        assertEquals(0, products.size());
    }

    @Test
    void itAllowsToAddProductDraft() {
        ProductCatalog catalog = thereIsProductCatalog();
        String productId = catalog.addProduct("lego-set-1", "super lego set");

        List<ProductData> products = catalog.getPublishedProducts();
        assertEquals(0, products.size());
        assertDoesNotThrow(()->{
            catalog.getProductById(productId);
        });
    }

    @Test
    void itDeniesToPublishProductWithoutPrice() {
        ProductCatalog catalog = thereIsProductCatalog();
        catalog.addProduct("1", "aaa");
        catalog.setImageUrl("1", "fsdfhfds");

        assertThrows(CantPublishProductException.class, ()->{
            catalog.publishProduct("1");
        });
    }

    @Test
    void itDeniesToPublishProductWithoutImageUrl() {
        ProductCatalog catalog = thereIsProductCatalog();
        catalog.addProduct("1", "aaa");
        catalog.setPrice("1", BigDecimal.valueOf(12.50));

        assertThrows(CantPublishProductException.class, ()->{
            catalog.publishProduct("1");
        });
    }

    @Test
    void itAllowsToPublishProduct() {
        ProductCatalog catalog = thereIsProductCatalog();
        catalog.addProduct("1", "aaa");
        catalog.setImageUrl("1", "http://fsdfhfds.com/pr1.png");
        catalog.setPrice("1", BigDecimal.valueOf(12.50));

        assertDoesNotThrow(()->{
            catalog.publishProduct("1");
        });

        List<ProductData> products = catalog.getPublishedProducts();

        assertEquals(1, products.size());
    }

    private ProductCatalog thereIsProductCatalog() {
        return new ProductCatalog();
    }
}