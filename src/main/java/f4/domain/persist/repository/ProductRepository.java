package f4.domain.persist.repository;

import f4.domain.persist.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Modifying
  @Query(value = "UPDATE product SET bid_user_id = :bidUserId, auction_price = :auctionPrice WHERE id = :productId",nativeQuery = true)
  void updateBidUserIdAndPriceById(@Param("productId") Long productId,@Param("bidUserId") Long bidUserId,@Param("auctionPrice") String auctionPrice);
}
