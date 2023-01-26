package br.com.compass.pb.msorder.application.service;

import br.com.compass.pb.msorder.application.ports.in.ItemUseCase;
import br.com.compass.pb.msorder.application.ports.out.ItemPortOut;
import br.com.compass.pb.msorder.application.ports.out.OrderPortOut;
import br.com.compass.pb.msorder.domain.dto.ItemDTO;
import br.com.compass.pb.msorder.domain.dto.ItemResponse;
import br.com.compass.pb.msorder.domain.dto.MessageOrderDTO;
import br.com.compass.pb.msorder.framework.adapters.out.event.topic.KafkaProducer;
import br.com.compass.pb.msorder.framework.exception.GenericException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService implements ItemUseCase {
    private final ItemPortOut portOut;
    private final OrderPortOut orderPortOut;
    private final ModelMapper modelMapper;
    private final KafkaProducer kafkaProducer;
    @Override
    public ItemResponse pachItem(Long id, ItemDTO itemDTO) throws JsonProcessingException {
        var item = portOut.findById(id)
                .orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Não foi possível localizar um item com este id!"));

        if(itemDTO.getExpirationDate().isBefore(item.getCreationDate()))
            throw new GenericException(HttpStatus.BAD_REQUEST, "data de expiração não pode ser anterior a data de criação!");

        var order = orderPortOut.findById(item.getOrder().getId());
        var total = order.get().getTotal();

        total = total.subtract(item.getValue());

        item.setExpirationDate(itemDTO.getExpirationDate());
        item.setValue(itemDTO.getValue());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());

        order.get().setTotal(total.add(itemDTO.getValue()));
        portOut.save(item);

        var messageDTO = MessageOrderDTO.builder()
                .orderId(order.get().getId())
                .total(order.get().getTotal())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(messageDTO);

        log.info("update in order, new value total");
        kafkaProducer.sendOrder(message);

        return modelMapper.map(item, ItemResponse.class);
    }
}
