package f4.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuctionRequestResult {
  SUCCESS("SUCCESS"),
  FAIL("FAIL"),
  ERROR("ERROR");

  private final String result;
}
