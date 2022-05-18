package pl.mszcz.productcatalog;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class JpaProductStorage implements ProductStorage {

    @Autowired
    private ProductDataRepository repo;

    @Override
    public ProductData save(ProductData newProduct) {
        return repo.save(newProduct);
    }

    @Override
    public Optional<ProductData> load(Long productId) {
        return repo.findById(productId);
    }

    @Override
    public List<ProductData> allPublished() {
        return repo
                .findAll()
                .stream()
                .filter(ProductData::isPublished)
                .toList();
    }
}
