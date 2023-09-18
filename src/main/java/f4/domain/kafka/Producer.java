package f4.domain.kafka;

import f4.domain.dto.SendToHistoryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

  @Value(value = "${kafka.produce.topic.name}")
  private String topicName;

  private final KafkaTemplate<Long, SendToHistoryDto> kafkaTemplate;

  public Producer(KafkaTemplate<Long, SendToHistoryDto> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void produce(SendToHistoryDto data) {
    kafkaTemplate.send(topicName, data);
  }
}
