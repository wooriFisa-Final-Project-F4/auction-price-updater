package f4.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

  // Bad Request 400
  BID_FAIL(400, "입찰 실패했습니다."),
  INVALID_AUCTION_STATUS(400, "잘못된 옥션 상태입니다."),
  NOT_EXISTS_RESULT(400, "해당 결과 상태는 존재하지 않습니다"),
  INVALID_AUCTION_STATUS_UPDATE(400, "상품의 경매 상태 변경을 실패하였습니다.");

  // Unathorized 401

  // Forbidden 402

  private final int code;
  private final String message;
}
