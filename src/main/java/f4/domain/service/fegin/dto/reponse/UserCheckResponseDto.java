package f4.domain.service.fegin.dto.reponse;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCheckResponseDto {

  @NotNull
  private Long userId;
  @NotBlank
  private String username;
  @Email
  @NotBlank
  private String Email;
}
