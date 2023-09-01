package f4.domain.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionRequestDto {

  @NotNull
  private Long productId;
  @NotNull
  private String price;
  @NotNull
  private String password;

}
