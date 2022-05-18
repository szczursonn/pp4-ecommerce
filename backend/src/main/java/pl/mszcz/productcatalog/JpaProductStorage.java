package pl.mszcz.productcatalog;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JpaProductStorage implements ProductStorage {

    @Autowired
    private ProductDataRepository repo;

    @Override
    public void save(ProductData newProduct) {
        repo.save(newProduct);
    }

    @Override
    public ProductData load(String productId) {
        return repo.getById(productId);
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
