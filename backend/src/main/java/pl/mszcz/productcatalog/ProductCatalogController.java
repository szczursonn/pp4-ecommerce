package pl.mszcz.productcatalog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/api/products/{id}")
    ProductData product(@PathVariable Long id) {
        return this.catalog.getProductById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("api/products/generate")
    ProductData mockProduct() {
        return this.catalog.generateMockProduct();
    }
}
