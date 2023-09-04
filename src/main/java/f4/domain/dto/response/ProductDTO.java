package f4.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

  private Long productId;
  private String bidPrice;
  private String bidTime;
  private Long bidUserId;
  private String productName;
  private String productImage;
  private String userEmail;
  private String bidStatus;
}
