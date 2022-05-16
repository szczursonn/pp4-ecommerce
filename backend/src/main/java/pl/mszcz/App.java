package pl.mszcz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.mszcz.ecommerce.NameProvider;
import pl.mszcz.productcatalog.*;
import pl.mszcz.sales.*;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    NameProvider createNameProvider() {
        return new NameProvider();
    }

    @Bean
    ProductStorage createProductStorage() {
        return new MapProductStorage();
    }

    @Bean
    ProductCatalog createMyProductCatalog(ProductStorage productStorage) throws CantPublishProductException {
        ProductCatalog productCatalog = new ProductCatalog(productStorage);
        String productId1 = productCatalog.addProduct("lego-set-1", "Nice Lego set");
        productCatalog.setImageUrl(productId1, "https://picsum.photos/id/237/200/300");
        productCatalog.setPrice(productId1, BigDecimal.TEN);
        productCatalog.publishProduct(productId1);

        String productId2 = productCatalog.addProduct("lego-set-2", "Even nicer Lego set");
        productCatalog.setImageUrl(productId2, "https://picsum.photos/id/238/200/300");
        productCatalog.setPrice(productId2, BigDecimal.valueOf(20.20));
        productCatalog.publishProduct(productId2);

        return productCatalog;
    }

    @Bean
    ProductDetailsProvider detailsProvider(ProductCatalog catalog) {
        return new RealProductDetailsProvider(catalog);
    }

    @Bean
    Sales createSales(ProductDetailsProvider productDetailsProvider) {
        return new Sales(new MapCartStorage(), productDetailsProvider);
    }

    @Bean
    CartStorage createCartStorage() {
        return new MapCartStorage();
    }
}