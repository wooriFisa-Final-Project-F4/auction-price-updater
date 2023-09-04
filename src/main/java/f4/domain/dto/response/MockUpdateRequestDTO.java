package f4.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockUpdateRequestDTO {

  private int option;
  private Long preUserId;
  private String preBidPrice;
  private Long curUserId;
  private String curBidPrice;
}
