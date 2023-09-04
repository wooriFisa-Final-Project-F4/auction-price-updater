package f4.domain.constant;

import f4.global.constant.CustomErrorCode;
import f4.global.exception.CustomException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuctionStatus {

  WAIT("wait"),
  PROGRESS("progress"),
  END("end");

  private final String command;

  public static AuctionStatus of(String command) {
    String lower = command.toLowerCase();

    return Arrays.stream(values())
        .filter(value -> value.getCommand().equals(lower))
        .findFirst()
        .orElseThrow(() -> new CustomException(CustomErrorCode.INVALID_AUCTION_STATUS));
  }
}
