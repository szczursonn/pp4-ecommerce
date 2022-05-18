package pl.mszcz.productcatalog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataRepository extends JpaRepository<ProductData, String> {

}
