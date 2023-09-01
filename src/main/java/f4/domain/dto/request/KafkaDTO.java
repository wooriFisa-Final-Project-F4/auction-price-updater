package f4.domain.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaDTO {

  private String key;
  private LocalDateTime time;
  private Long userId;
  private AuctionRequestDto request;
}
