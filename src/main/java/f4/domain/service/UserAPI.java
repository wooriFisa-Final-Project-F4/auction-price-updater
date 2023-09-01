package f4.domain.service;

import f4.domain.dto.response.FeignUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface UserAPI {

  @GetMapping("user/v1/{id}")
  public FeignUserDto getUser(@PathVariable Long id);
}
