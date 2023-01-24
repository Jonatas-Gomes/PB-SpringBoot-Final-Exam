package br.com.compass.pb.msorder.framework.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    @Value("${topic.order-topic}")
    private String orderTopicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrder(String message){
        kafkaTemplate.send(orderTopicName, message);
        log.info("Message sent with sucess!");
    }

}
