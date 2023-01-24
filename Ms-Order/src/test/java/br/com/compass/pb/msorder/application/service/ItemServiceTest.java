package br.com.compass.pb.msorder.application.service;

import br.com.compass.pb.msorder.application.ports.out.ItemPortOut;
import br.com.compass.pb.msorder.application.ports.out.OrderPortOut;
import br.com.compass.pb.msorder.domain.dto.ItemDTO;
import br.com.compass.pb.msorder.domain.dto.ItemResponse;
import br.com.compass.pb.msorder.domain.model.Address;
import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;
import br.com.compass.pb.msorder.framework.exception.GenericException;
import br.com.compass.pb.msorder.framework.kafka.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemService service;
    @Spy
    private ModelMapper mapper;

    @Mock
    private KafkaProducer kafkaProducer;
    @Mock
    private ItemPortOut portOut;
    @Mock
    private OrderPortOut orderPortOut;

    private static final Long ID=1L;

    private final BigDecimal VALUE = new BigDecimal("30");

    private final String CEP = "41200150";

    private final String CPF = "08010383597";


    @Test
    void shouldSucessWhenPatch() throws JsonProcessingException {
        var item = getItems();
        var order = getOrder();
        var itemDTO = getItemDTO();
        var itemResponse = getItemResponse();

        when(portOut.findById(ID)).thenReturn(Optional.of(item));
        when(orderPortOut.findById(ID)).thenReturn(Optional.of(order));
        when(mapper.map(item, ItemResponse.class)).thenReturn(itemResponse);
        when(portOut.save(item)).thenReturn(item);

        doNothing().when(kafkaProducer).sendOrder(any());

        var response = service.pachItem(ID, itemDTO);
        verify(portOut).save(item);

        Assertions.assertEquals(itemDTO.getValue(), response.getValue());
    }

    @Test
    void throw_GenericException_When_Try_Patch_With_IdInvalid(){
        var itemDTO = getItemDTO();
        when(portOut.findById(ID)).thenReturn(Optional.empty());

        assertThrows(GenericException.class, () -> {
            service.pachItem(ID, itemDTO);
        });

    }
    @Test
    void throw_GenericException_When_Try_Patch_With_ExpirationDateInvalid(){
        var itemDTO = getItemDTO();
        itemDTO.setExpirationDate(LocalDate.of(2023, 02, 19));

        assertThrows(GenericException.class, () -> {
            service.pachItem(ID, itemDTO);
        });

    }


    private ItemResponse getItemResponse() {
        return ItemResponse.builder()
                .name("anyName")
                .description("this is description")
                .value(VALUE)
                .expirationDate(LocalDate.now())
                .build();
    }


    private ItemDTO getItemDTO() {
        return ItemDTO.builder()
                .value(VALUE)
                .description("this is description")
                .name("anyName")
                .expirationDate(LocalDate.now())
                .build();
    }

    private Item getItems() {
        return Item.builder().creationDate(LocalDate.now())
                .expirationDate(LocalDate.now())
                .value(VALUE)
                .description("this is description")
                .name("anyName").id(ID)
                .order(getOrder())
                .build();

    }

    private Order getOrder(){
        return Order.builder()
                .id(ID)
                .address(getAddress())
                .total(VALUE)
                .cpf(CPF)
                .build();
    }

    private Address getAddress(){
        return Address.builder()
                .id(ID)
                .city("Salvador")
                .state("BA")
                .cep(CEP)
                .number("82")
                .street("Travessa Senhor do Bonfim")
                .build();
    }

}
