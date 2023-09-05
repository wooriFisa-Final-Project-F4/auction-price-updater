package f4.domain.persist.repository;

import f4.domain.persist.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query(value = "UPDATE product SET bid_user_id = :bidUserId, auction_price = :auctionPrice WHERE id =: productId",nativeQuery = true)
  void updateBidUserIdAndPriceById(Long productId, Long bidUserId, String auctionPrice);
}
