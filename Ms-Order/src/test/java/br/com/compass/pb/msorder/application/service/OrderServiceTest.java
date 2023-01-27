package br.com.compass.pb.msorder.application.service;

import br.com.compass.pb.msorder.application.ports.out.OrderPortOut;
import br.com.compass.pb.msorder.domain.dto.*;
import br.com.compass.pb.msorder.domain.model.Address;
import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;
import br.com.compass.pb.msorder.framework.adapters.out.event.topic.KafkaProducer;
import br.com.compass.pb.msorder.framework.exception.GenericException;
import br.com.compass.pb.msorder.framework.viacep.ViaCepClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService service;
    @Mock
    private OrderPortOut portOut;
    @Spy
    private ModelMapper mapper;
    @Mock
    private ViaCepClient viaCepClient;
    @Mock
    private KafkaProducer kafkaProducer;

    private static final Long ID=1L;
    private final String CPF = "93267686512";
    private final String CEP = "41200150";
    private final String CEP_INVALID = "99999999";
    private final BigDecimal VALUE = new BigDecimal("30");




    @Test
    void shouldSucessWhenCreateOrder() throws JsonProcessingException {
        var orderDTO = getOrderDTO();
        var order = getOrder();
        var addressDTO = getAddressDTO();

        when(mapper.map(orderDTO, Order.class)).thenReturn(order);

        when(viaCepClient.findByCep(CEP)).thenReturn(addressDTO);

        when(portOut.save(order)).thenReturn(order);
        doNothing().when(kafkaProducer).sendOrder(any());

        service.createOrder(orderDTO);

        verify(portOut).save(order);
        assertEquals("Salvador", order.getAddress().getCity());
    }

    @Test
    void should_Exception_When_Try_CreateOrder_With_CepInvalid(){
        var orderDTO = getOrderDTO();
        var order = getOrder();
        orderDTO.getAddress().setCep(CEP_INVALID);
        when(mapper.map(orderDTO, Order.class)).thenReturn(order);
        when(viaCepClient.findByCep("99999999")).thenReturn(new AddressDTO());

        assertThrows(GenericException.class, () -> {
            service.createOrder(orderDTO);
        });

    }
    @Test
    void shouldGenericExceptionWhen_TryCreateOrder_With_ExpirationDate_Invalid(){
        var orderDTO = getOrderDTO();
        orderDTO.getItems().get(0).setExpirationDate(LocalDate.of(2023, 01, 19));
        var order = getOrder();
        var addressDTO = getAddressDTO();

        when(mapper.map(orderDTO, Order.class)).thenReturn(order);
        when(viaCepClient.findByCep(CEP)).thenReturn(addressDTO);

        assertThrows(GenericException.class, () -> {
            service.createOrder(orderDTO);
        });
    }
    @Test
    void findByIdTest(){
        var order = getOrder();
        var orderResponse = getOrderResponse();
        when(portOut.findById(anyLong())).thenReturn(Optional.of(order));
        portOut.findById(ID);

        var response = portOut.findById(ID);
        assertEquals(orderResponse.getOrder().getId(), response.get().getId());
    }
    @Test
    void throw_GenericException_When_Try_FindById_With_IdInvalid(){
        when(portOut.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(GenericException.class, () -> {
            service.findById(ID);
        });
    }

    @Test
    void shouldSucessWhenFindAll_Without_Cpf(){
        Pageable pageable = mock(Pageable.class);
        Page<Order> page = mock(Page.class);
        var order = getOrder();

        when(page.getContent()).thenReturn(List.of(order));

        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);
        when(page.getTotalPages()).thenReturn(1);

        when(portOut.findAll(pageable)).thenReturn(page);
        PageableResponse response = service.findall(null, pageable);


        Assertions.assertEquals(1, response.getNumberOfElements());
        Assertions.assertEquals(1L, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());

        Assertions.assertEquals(response.getOrders().get(0).getId(), order.getId());

    }
    @Test
    void shoudlSucessWhenFindallWithCpf(){
        Pageable pageable = mock(Pageable.class);
        Page<Order> page = mock(Page.class);
        var order = getOrder();

        when(page.getContent()).thenReturn(List.of(order));

        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);
        when(page.getTotalPages()).thenReturn(1);

        when(portOut.findByCpf(CPF, pageable)).thenReturn(page);
        PageableResponse response = service.findall(CPF, pageable);


        Assertions.assertEquals(1, response.getNumberOfElements());
        Assertions.assertEquals(1L, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());

        Assertions.assertEquals(response.getOrders().get(0).getId(), order.getId());
    }
    @Test
    void throw_GenericException_When_Try_FindAll_With_Cpf_Nonexistent(){
        Pageable pageable = mock(Pageable.class);
        Page<Order> page = Page.empty();
        String cpf = "99999999999";

        when(portOut.findByCpf(cpf, pageable)).thenReturn(page);

        assertThrows(GenericException.class, () -> service.findall(cpf, pageable));

    }

    @Test
    void shouldSucessWhenDeleteById(){
        var order = getOrder();
        when(portOut.findById(anyLong())).thenReturn(Optional.of(order));
        doNothing().when(portOut).deleteById(anyLong());
        service.delete(ID);
        verify(portOut).deleteById(ID);
    }
    @Test
    void throw_GenericException_When_Try_Delete_With_IdInvalid(){
        when(portOut.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(GenericException.class, () -> {
            service.delete(ID);
        });
    }
    @Test
    void shouldSucessWhenUpdate(){
        var orderUpdate = getOrderUpdateDTO();
        var order = getOrder();
        when(portOut.findById(anyLong())).thenReturn(Optional.of(order));
        var addressDTO = getAddressDTO();
        when(viaCepClient.findByCep(CEP)).thenReturn(addressDTO);
        when(portOut.save(order)).thenReturn(order);

        var response = service.update(ID, orderUpdate);

        Assertions.assertEquals(orderUpdate.getCpf(), response.getOrder().getCpf());
    }
    @Test
    void throw_GenericException_When_Try_Update_With_IdInvalid(){
        var orderUpdate = getOrderUpdateDTO();
        when(portOut.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GenericException.class, () -> {
            service.update(ID, orderUpdate);
        });
    }
    @Test
    void throw_GenericException_When_Try_Update_With_CepInvalid(){
        var orderUpdate = getOrderUpdateDTO();
        var order = getOrder();
        orderUpdate.getAddress().setCep(CEP_INVALID);


        when(portOut.findById(anyLong())).thenReturn(Optional.of(order));
        when(viaCepClient.findByCep(CEP_INVALID)).thenReturn(new AddressDTO());

        assertThrows(GenericException.class, () -> {
            service.update(ID, orderUpdate);
        });



    }


    private OrderUpdateDTO getOrderUpdateDTO() {
        return OrderUpdateDTO.builder()
                .cpf(CPF)
                .address(getAddressRequestDTO())
                .build();
    }

    private AddressDTO getAddressDTO() {
        return AddressDTO.builder()
                .uf("BA")
                .bairro("Engomadeira")
                .localidade("Salvador")
                .complemento("E")
                .logradouro("Travessa Senhor do Bonfim")
                .build();
    }


    private OrderDTO getOrderDTO(){
        return OrderDTO.builder()
                .cpf(CPF)
                .items(getItemsDTO())
                .address(getAddressRequestDTO())
                .build();
    }

    private List<ItemDTO> getItemsDTO() {
        var item = ItemDTO.builder()
                .value(VALUE)
                .description("this is description")
                .name("anyName")
                .expirationDate(LocalDate.now())
                .build();
        return List.of(item);
    }

    private AddressRequestDTO getAddressRequestDTO(){
        return AddressRequestDTO.builder()
                .cep(CEP)
                .number("82")
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

    private OrderResponse getOrderResponse(){
        return new OrderResponse(getOrder());
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
