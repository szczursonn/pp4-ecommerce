package pl.mszcz.productcatalog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MockProductGenerator {

    public ProductData generate() {

        Random r = new Random();
        String name = Integer.toString(Math.abs(r.nextInt() % 1000000), 20);
        BigDecimal price = BigDecimal.valueOf(Math.abs(r.nextInt() % 25000)/100.0).setScale(2, RoundingMode.CEILING);
        String image = "https://picsum.photos/id/" + Integer.toString(r.nextInt() % 1000) + "/200/300";
        ProductData product = new ProductData(
                null,
                name,
                price
        );
        product.setImageUrl(image);
        return product;
    }
}
