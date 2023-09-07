package f4.domain.service.impl;

import f4.domain.constant.AuctionRequestResult;
import f4.domain.dto.KafkaDto;
import f4.domain.service.fegin.dto.reponse.UserCheckResponseDto;
import f4.domain.dto.response.MockUpdateRequestDto;
import f4.domain.dto.SendToHistoryDto;
import f4.domain.kafka.Producer;
import f4.domain.persist.entity.Product;
import f4.domain.persist.repository.ProductImageRepository;
import f4.domain.persist.repository.ProductRepository;
import f4.domain.service.AuctionPriceService;
import f4.domain.service.fegin.UserServiceAPI;
import f4.global.constant.CustomErrorCode;
import f4.global.exception.CustomException;
import feign.FeignException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionPriceServiceImpl implements AuctionPriceService {

  private final Producer producer;
  private final ProductRepository productRepository;
  private final ProductImageRepository imageRepository;
  private final UserServiceAPI userServiceAPI;

  @Value(value = "${mock.url}")
  private String url;

  @Override
  @Transactional
  public void checkAndUpdate(KafkaDto kafkaDto) {
    Long productId = kafkaDto.getRequest().getProductId();
    Product product = findByProductId(productId);
    String imageUrl = loadByProductIdOrderByProductId(productId);

    SendToHistoryDto sendToHistoryDto = standByProductDto(kafkaDto, product, imageUrl);

    try {
      UserCheckResponseDto userCheckResponse = userServiceAPI.findByUserIdForOtherService(
          kafkaDto.getUserId());

      sendToHistoryDto.setUserEmail(userCheckResponse.getEmail());

//    Mock API 요청
      sendBidRequest(product, sendToHistoryDto);

      // Product 요청
      productRepository.updateBidUserIdAndPriceById(
          product.getId(),
          sendToHistoryDto.getBidUserId(),
          sendToHistoryDto.getBidPrice()
      );
      sendToHistoryDto.setBidStatus(AuctionRequestResult.SUCCESS.getResult());
    } catch (CustomException e) {
      sendToHistoryDto.setBidStatus(AuctionRequestResult.FAIL.getResult());
      log.info("입찰에 실패하셨습니다. Error Message : {}", e.getCustomErrorCode().getMessage());
    } catch (FeignException e) {
      sendToHistoryDto.setBidStatus(AuctionRequestResult.ERROR.getResult());
      log.info("서버 에러가 발생했습니다. Error Message : {}", e.getMessage());
    } finally {
      producer.produce(sendToHistoryDto);
    }
  }

  private Product findByProductId(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_EXIST_PRODUCT));
  }

  private String loadByProductIdOrderByProductId(Long productId) {
    return imageRepository.findByProductIdOrderByIdAsc(productId)
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_EXIST_IMAGE));
  }

  public SendToHistoryDto standByProductDto(
      KafkaDto kafkaRequest, Product product, String imageUrl) {
    return SendToHistoryDto.builder()
        .productId(kafkaRequest.getRequest().getProductId())
        .productName(product.getName())
        .productImage(imageUrl)
        .bidPrice(kafkaRequest.getRequest().getPrice())
        .bidTime(kafkaRequest.getTime().toString())
        .bidUserId(kafkaRequest.getUserId())
        .build();
  }

  public void restTemplateForBid(MockUpdateRequestDto mock) {
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

  public void sendBidRequest(Product product, SendToHistoryDto sendProductInfo) {
    if (!comparePrice(product, sendProductInfo)) {
      throw new CustomException(CustomErrorCode.LESS_THEN_PRE_PRICE);
    }

    MockUpdateRequestDto mockUpdateRequestDto = standByBidOption(product, sendProductInfo);
    restTemplateForBid(mockUpdateRequestDto);
  }

  private MockUpdateRequestDto standByBidOption(Product product,
      SendToHistoryDto sendProductInfo) {

    if (product.getBidUserId() == null) {
      return option1MockRequestBuilder(sendProductInfo);
    }

    if (product.getBidUserId().equals(sendProductInfo.getBidUserId())) {
      throw new CustomException(CustomErrorCode.CURRENT_BID_USER);
    }

    return option2MockRequestBuilder(product, sendProductInfo);
  }

  public MockUpdateRequestDto option1MockRequestBuilder(
      SendToHistoryDto sendProductInfo) {
    return MockUpdateRequestDto.builder()
        .curUserId(sendProductInfo.getBidUserId())
        .curBidPrice(sendProductInfo.getBidPrice())
        .option(1)
        .build();
  }

  public MockUpdateRequestDto option2MockRequestBuilder(
      Product product,
      SendToHistoryDto sendProductInfo) {
    return MockUpdateRequestDto.builder()
        .preUserId(product.getBidUserId())
        .preBidPrice(product.getAuctionPrice())
        .curUserId(sendProductInfo.getBidUserId())
        .curBidPrice(sendProductInfo.getBidPrice())
        .option(2)
        .build();
  }

  private boolean comparePrice(Product product, SendToHistoryDto sendProductInfo) {
    return Long.parseLong(product.getAuctionPrice()) < Long.parseLong(
        sendProductInfo.getBidPrice());
  }
}
