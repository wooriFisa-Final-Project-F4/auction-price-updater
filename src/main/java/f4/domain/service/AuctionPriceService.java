package f4.domain.service;

import f4.domain.dto.KafkaDto;

public interface AuctionPriceService {

    void checkAndUpdate(KafkaDto kafkaDto);
}