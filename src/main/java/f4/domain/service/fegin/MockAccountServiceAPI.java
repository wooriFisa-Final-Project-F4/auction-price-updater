package f4.domain.service.fegin;

import f4.domain.dto.response.MockUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name ="MOCK" ,url="${mock.url}")
public interface MockAccountServiceAPI {
  @PutMapping("/woori/account/v1/bid")
  void bidInfoUpdate(MockUpdateRequestDto mockUpdateRequestDto);
}
