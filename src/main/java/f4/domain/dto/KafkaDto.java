package f4.domain.dto;

import f4.domain.dto.request.AuctionRequestDto;
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
public class KafkaDto {

  private Long userId;
  private String key;
  private LocalDateTime time;
  private AuctionRequestDto request;
}
