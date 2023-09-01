package f4.domain.persist.repository;

import f4.domain.persist.entity.ProductImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
  public List<ProductImage> findByProductIdOrderByIdAsc(Long productId);
}
