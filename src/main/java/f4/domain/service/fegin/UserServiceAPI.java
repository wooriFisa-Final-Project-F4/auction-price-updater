package f4.domain.service.fegin;

import f4.domain.service.fegin.dto.reponse.UserCheckResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface UserServiceAPI {

  @GetMapping("/user/v1/feign/{userId}")
  UserCheckResponseDto findByUserIdForOtherService(@PathVariable("userId") Long userId);
}
