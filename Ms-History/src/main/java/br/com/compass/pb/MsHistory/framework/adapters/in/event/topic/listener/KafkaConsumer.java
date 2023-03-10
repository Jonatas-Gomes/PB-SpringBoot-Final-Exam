package br.com.compass.pb.MsHistory.framework.adapters.in.event.topic.listener;

import br.com.compass.pb.MsHistory.application.port.in.HistoryUseCase;
import br.com.compass.pb.MsHistory.application.port.out.HistoryPortOut;
import br.com.compass.pb.MsHistory.domain.dto.MessageOrderDTO;
import br.com.compass.pb.MsHistory.domain.model.History;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final HistoryUseCase useCase;

    @KafkaListener(topics = "${topic.order-topic}", groupId = "group_id")
    public void consumeOrder(ConsumerRecord<String, String> payload) throws JsonProcessingException {
        log.info("message consumed");

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        var messageOrderDTO = mapper.readValue(payload.value(), MessageOrderDTO.class);
        useCase.messageFilter(payload.timestamp(), messageOrderDTO);
    }
}
