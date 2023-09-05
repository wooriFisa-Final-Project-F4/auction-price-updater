package f4.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendToHistoryDto {

  private Long productId;
  private String bidPrice;
  private String bidTime;
  private Long bidUserId;
  private String productName;
  private String productImage;
  private String userEmail;
  private String bidStatus;
}
