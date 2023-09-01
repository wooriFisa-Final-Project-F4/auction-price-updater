package f4.domain.service.impl;

import f4.domain.constant.AuctionRequestResult;
import f4.domain.dto.response.MockUpdateRequestDTO;
import f4.domain.dto.response.ProductDTO;
import f4.domain.dto.request.KafkaDTO;
import f4.domain.persist.entity.Product;
import f4.domain.persist.entity.ProductImage;
import f4.domain.persist.repository.ProductImageRepository;
import f4.domain.persist.repository.ProductRepository;
import f4.domain.dto.response.FeignUserDto;
import f4.domain.kafka.Producer;
import f4.domain.service.PriceService;
import f4.domain.service.UserAPI;
import f4.global.constant.CustomErrorCode;
import f4.global.exception.CustomException;
import feign.FeignException;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceServiceImpl implements PriceService {

  private final ProductRepository repository;
  private final ProductImageRepository imageRepository;
  private final Producer producer;
  private final UserAPI userAPI;
  private final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

  @Value(value = "${mock.url}")
  private String url;

  @Override
  @Transactional
  public void checkAndUpdate(KafkaDTO kafkaRequest){
    Product product = repository.findById(kafkaRequest.getRequest().getProductId()).get();
    ProductImage image = imageRepository.findByProductIdOrderByIdAsc(product.getId()).get(0);
    ProductDTO sendProductInfo = productDtoBuilder(kafkaRequest, product, image);

    try {
      FeignUserDto user = userAPI.getUser(kafkaRequest.getUserId());
      sendProductInfo.setUserEmail(user.getEmail());

      mockBuildAndSend(product, sendProductInfo);
      updateProduct(product, sendProductInfo);
      sendProductInfo.setBidStatus(AuctionRequestResult.SUCCESS.getResult());
    } catch (CustomException ce) {
      sendProductInfo.setBidStatus(AuctionRequestResult.FAIL.getResult());
      logger.info("FAIL : { }", ce);
    } catch (FeignException e) {
      sendProductInfo.setBidStatus(AuctionRequestResult.ERROR.getResult());
      logger.error("error : { }", e);
    } finally {
      producer.produce(sendProductInfo);
    }

  }

  public ProductDTO productDtoBuilder(KafkaDTO kafkaRequest, Product product, ProductImage image) {
    return ProductDTO.builder()
        .productId(kafkaRequest.getRequest().getProductId())
        .productName(product.getName())
        .productImage(image.getImageUrl())
        .bidPrice(kafkaRequest.getRequest().getPrice())
        .bidTime(kafkaRequest.getTime().toString())
        .bidUserId(kafkaRequest.getUserId())
        .build();
  }

  public void restTemplateToMock(MockUpdateRequestDTO mock) {
    URI uri = UriComponentsBuilder
        .fromUriString(url)
        .path("/woori/account/v1/bid")
        .encode()
        .build()
        .expand("Flature")
        .toUri();
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.put(uri, mock);
  }

  public void mockBuildAndSend(Product product, ProductDTO sendProductInfo)
      throws CustomException {
    if (nullSuccess(product, sendProductInfo)) {
      restTemplateToMock(option1Builder(sendProductInfo));
    } else if (notNullSuccess(product, sendProductInfo)) {
      restTemplateToMock(option2Builder(sendProductInfo, product));
    } else {
      producer.produce(sendProductInfo);
      throw new CustomException(CustomErrorCode.BID_FAIL);
    }
  }

  public MockUpdateRequestDTO option1Builder(ProductDTO sendProductInfo) {
    return MockUpdateRequestDTO.builder()
        .curUserId(sendProductInfo.getBidUserId())
        .curBidPrice(sendProductInfo.getBidPrice())
        .option(1)
        .build();
  }

  public MockUpdateRequestDTO option2Builder(ProductDTO sendProductInfo, Product product) {
    return MockUpdateRequestDTO.builder()
        .preUserId(product.getBidUserId())
        .preBidPrice(product.getAuctionPrice())
        .curUserId(sendProductInfo.getBidUserId())
        .curBidPrice(sendProductInfo.getBidPrice())
        .option(2)
        .build();
  }

  public boolean nullSuccess(Product product, ProductDTO sendProductInfo) {
    return (product.getBidUserId() == null &&
        Long.parseLong(product.getAuctionPrice()) < Long.parseLong(sendProductInfo.getBidPrice()));
  }

  public boolean notNullSuccess(Product product, ProductDTO sendProductInfo) {
    return (product.getBidUserId() != null &&
        !product.getBidUserId().equals(sendProductInfo.getBidUserId()) &&
        Long.parseLong(product.getAuctionPrice()) < Long.parseLong(sendProductInfo.getBidPrice())
    );
  }

  public void updateProduct(Product product, ProductDTO sendProductInfo) {
    product.setAuctionPrice(sendProductInfo.getBidPrice());
    product.setBidUserId(sendProductInfo.getBidUserId());
    repository.save(product);
  }
}
