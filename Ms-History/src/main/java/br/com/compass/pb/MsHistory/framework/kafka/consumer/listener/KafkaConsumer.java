package br.com.compass.pb.MsHistory.framework.kafka.consumer.listener;

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

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final HistoryPortOut portOut;

    @KafkaListener(topics = "${topic.order-topic}", groupId = "group_id")
    public void consumeOrder(ConsumerRecord<String, String> payload) throws JsonProcessingException {
        log.info("message consumed");

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        var messageOrderDTO = mapper.readValue(payload.value(), MessageOrderDTO.class);
        var history = portOut.findByIdOrder(messageOrderDTO.getOrderId());

        if(history.isPresent()){
            updateHistoryOrder(payload.timestamp(), history, messageOrderDTO.getTotal());
            log.info("order history updated!");
        }else {
            saveMessageOrder(payload.timestamp(), messageOrderDTO);
            log.info("order message saved!");
        }
    }

    private void updateHistoryOrder(long timestamp, Optional<History> history, BigDecimal total) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
        LocalDate date = time.toLocalDate();

        portOut.updateHistory(history.get().getIdOrder(), total, date);
    }

    private void saveMessageOrder(Long timestamp, MessageOrderDTO messageOrderDTO) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
        LocalDate date = time.toLocalDate();

        var history = History.builder()
                .idOrder(messageOrderDTO.getOrderId())
                .total(messageOrderDTO.getTotal())
                .eventDate(date)
                .build();

        portOut.save(history);
    }
}
