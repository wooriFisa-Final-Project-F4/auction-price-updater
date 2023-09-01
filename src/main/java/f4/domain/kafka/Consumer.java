package f4.domain.kafka;

import f4.domain.dto.request.KafkaDTO;
import f4.domain.service.impl.PriceServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {

  private final PriceServiceImpl priceService;

  @KafkaListener(topics = "${kafka.topic.name}")
  public void consume(ConsumerRecord<String, KafkaDTO> record) {
    try {
      priceService.checkAndUpdate(record.value());
    } catch (Exception e) {
      log.error("error : { }", e);
    }
  }
}
