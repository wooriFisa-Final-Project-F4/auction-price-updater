package f4.domain.kafka;

import f4.domain.dto.request.KafkaDto;
import f4.domain.service.impl.AuctionPriceServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Consumer {

  private final AuctionPriceServiceImpl priceService;

  @KafkaListener(topics = "${kafka.topic.name}")
  public void consume(ConsumerRecord<String, KafkaDto> record) {
    try {
      priceService.checkAndUpdate(record.value());
    } catch (Exception e) {
      log.error("error : { }", e);
    }
  }
}
