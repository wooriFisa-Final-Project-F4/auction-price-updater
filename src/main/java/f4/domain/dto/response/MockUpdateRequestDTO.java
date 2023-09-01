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
public class MockUpdateRequestDTO {

  private Long preUserId;
  private String preBidPrice;
  private Long curUserId;
  private String curBidPrice;
  private int option;
}
