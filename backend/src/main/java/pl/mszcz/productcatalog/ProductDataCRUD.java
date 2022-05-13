package pl.mszcz.productcatalog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataCRUD extends JpaRepository<ProductData, String> {

}
