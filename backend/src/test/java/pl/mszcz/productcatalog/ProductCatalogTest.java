package pl.mszcz.productcatalog;

import org.junit.jupiter.api.Test;
import pl.mszcz.productcatalog.exceptions.CantPublishProductException;

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
        Long productId = catalog.addProduct("super lego set").getId();

        List<ProductData> products = catalog.getPublishedProducts();
        assertEquals(0, products.size());
        assertDoesNotThrow(()->{
            catalog.getProductById(productId);
        });
    }

    @Test
    void itDeniesToPublishProductWithoutPrice() {
        ProductCatalog catalog = thereIsProductCatalog();
        Long productId = catalog.addProduct("giga lego set 2000").getId();
        catalog.setImageUrl(productId, "fsdfhfds");

        assertThrows(CantPublishProductException.class, ()->{
            catalog.publishProduct(productId);
        });
    }

    @Test
    void itDeniesToPublishProductWithoutImageUrl() {
        ProductCatalog catalog = thereIsProductCatalog();
        Long productId = catalog.addProduct("lego set").getId();
        catalog.setPrice(productId, BigDecimal.valueOf(12.50));

        assertThrows(CantPublishProductException.class, ()->{
            catalog.publishProduct(productId);
        });
    }

    @Test
    void itAllowsToPublishProduct() {
        ProductCatalog catalog = thereIsProductCatalog();
        Long productId = catalog.addProduct("produkcik").getId();
        catalog.setImageUrl(productId, "http://fsdfhfds.com/pr1.png");
        catalog.setPrice(productId, BigDecimal.valueOf(12.50));

        assertDoesNotThrow(()->{
            catalog.publishProduct(productId);
        });

        List<ProductData> products = catalog.getPublishedProducts();

        assertEquals(1, products.size());
    }

    private ProductCatalog thereIsProductCatalog() {
        ProductStorage productStorage = new MapProductStorage();
        return new ProductCatalog(productStorage);
    }
}