package f4.domain.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionTimeStatusDto implements Serializable {
  private String auctionStatus;
  private LocalDateTime auctionStartTime;
  private LocalDateTime auctionEndTime;

}
