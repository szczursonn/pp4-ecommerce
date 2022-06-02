package pl.mszcz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.mszcz.ecommerce.NameProvider;
import pl.mszcz.payu.PayU;
import pl.mszcz.productcatalog.*;
import pl.mszcz.sales.*;
import pl.mszcz.sales.cart.CartItemStorage;
import pl.mszcz.sales.cart.JpaCartItemStorage;
import pl.mszcz.sales.purchase.*;

import java.math.BigDecimal;

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
        return new JpaProductStorage();
    }

    @Bean
    ProductCatalog createMyProductCatalog(ProductStorage productStorage) {
        ProductCatalog productCatalog = new ProductCatalog(productStorage);

        createDefaultProducts(productCatalog);

        return productCatalog;
    }

    @Bean
    ProductDetailsProvider detailsProvider(ProductCatalog catalog) {
        return new RealProductDetailsProvider(catalog);
    }

    @Bean
    Sales createSales(ProductDetailsProvider productDetailsProvider, CartItemStorage cartItemStorage, PurchaseStorage purchaseStorage, PaymentGateway paymentGateway) {
        return new Sales(productDetailsProvider, cartItemStorage, purchaseStorage, paymentGateway);
    }

    @Bean
    CartItemStorage createCartItemStorage() {
        return new JpaCartItemStorage();
    }

    @Bean
    PurchaseStorage createPurchaseStorage() {
        return new JpaPurchaseStorage();
    }

    @Bean
    PaymentGateway createPaymentGateway() {
        PayU payU = new PayU("300746", "");
        return new PayUPaymentGateway(payU);
    }

    private void createDefaultProducts(ProductCatalog productCatalog) {
        Long productId1 = productCatalog.addProduct("Lego set 1", BigDecimal.TEN).getId();
        productCatalog.setImageUrl(productId1, "https://picsum.photos/id/237/200/300");

        Long productId2 = productCatalog.addProduct("Lego set 2", BigDecimal.valueOf(20.20)).getId();
        productCatalog.setImageUrl(productId2, "https://picsum.photos/id/238/200/300");
    }

}