package pl.mszcz.productcatalog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductCatalogController {
    private final ProductCatalog catalog;

    ProductCatalogController(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping("/api/products")
    List<ProductData> products() {
        return this.catalog.getPublishedProducts();
    }
}
