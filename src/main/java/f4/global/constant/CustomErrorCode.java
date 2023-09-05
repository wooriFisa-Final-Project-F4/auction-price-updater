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
  INVALID_AUCTION_STATUS_UPDATE(400, "상품의 경매 상태 변경을 실패하였습니다."),
  LESS_THEN_PRE_PRICE(400, "기존 입찰 금액보다 입찰 금액이 낮습니다."),
  CURRENT_BID_USER(400, "이미 현재 입찰 확정자입니다."),
  // Unathorized 401

  // Forbidden 402

  // Not Found 404
  NOT_EXIST_PRODUCT(404, "해당 상품이 존재하지 않습니다."),
  NOT_EXIST_IMAGE(404, "대표 이미지가 존재하지 없습니다."),
  ;

  private final int code;
  private final String message;
}
