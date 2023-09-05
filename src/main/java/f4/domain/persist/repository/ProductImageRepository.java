package f4.domain.persist.repository;

import f4.domain.persist.entity.ProductImage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

  @Query(value = "SELECT image_url from product_image WHERE product_id = :productId ORDER BY id LIMIT 1", nativeQuery = true)
  Optional<String> findByProductIdOrderByIdAsc(Long productId);
}
