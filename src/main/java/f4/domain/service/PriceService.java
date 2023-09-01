package f4.domain.service;

import f4.domain.dto.request.KafkaDTO;

public interface PriceService {

    void checkAndUpdate(KafkaDTO kafkaRequset);
}
