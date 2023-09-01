package f4.domain.kafka;

import f4.domain.dto.response.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

  @Value(value = "${kafka.produce.topic.name}")
  private String topicName;

  private KafkaTemplate<String, ProductDTO> kafkaTemplate;

  public Producer(KafkaTemplate<String, ProductDTO> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void produce(ProductDTO data){
    kafkaTemplate.send(topicName,data.getProductName(),data);
  }
}
