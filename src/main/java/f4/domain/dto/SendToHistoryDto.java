package f4.domain.dto;

import java.time.LocalDateTime;
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
  private LocalDateTime bidTime;
  private Long bidUserId;
  private String productName;
  private String productImage;
  private String userEmail;
  private String bidStatus;
}
