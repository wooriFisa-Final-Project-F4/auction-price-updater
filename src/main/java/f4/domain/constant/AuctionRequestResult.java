package f4.domain.constant;

import f4.global.constant.CustomErrorCode;
import f4.global.exception.CustomException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuctionRequestResult {

  SUCCESS("SUCCESS"),
  FAIL("FAIL"),
  ERROR("ERROR");

  private final String result;

  public static AuctionRequestResult of(String result) {
    return Arrays.stream(values())
        .filter(i -> i.getResult().equals(result))
        .findFirst()
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_EXISTS_RESULT));
  }
}
