package pl.mszcz.productcatalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDataRepository extends JpaRepository<ProductData, Long> {
    List<ProductData> findByArchivedFalse();
}
