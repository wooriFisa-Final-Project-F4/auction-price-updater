package f4.domain.kafka;

import f4.domain.dto.response.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

  @Value(value = "${kafka.produce.topic.name}")
  private String topicName;

  private KafkaTemplate<String, ProductDto> kafkaTemplate;

  public Producer(KafkaTemplate<String, ProductDto> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void produce(ProductDto data) {
    kafkaTemplate.send(topicName, data.getProductName(), data);
  }
}
