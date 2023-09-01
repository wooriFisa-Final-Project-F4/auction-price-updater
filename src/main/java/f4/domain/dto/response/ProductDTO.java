package f4.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
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
