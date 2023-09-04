package f4.domain.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionTimeStatusDto implements Serializable {

  @NotBlank
  private String auctionStatus;
  private LocalDateTime auctionStartTime;
  private LocalDateTime auctionEndTime;

}
