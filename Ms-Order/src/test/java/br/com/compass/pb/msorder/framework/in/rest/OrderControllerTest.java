package br.com.compass.pb.msorder.framework.in.rest;

import br.com.compass.pb.msorder.application.service.OrderService;
import br.com.compass.pb.msorder.domain.dto.*;
import br.com.compass.pb.msorder.domain.model.Address;
import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @MockBean
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;

    private Gson gson;
    @Spy
    private ObjectMapper objectMapper;

    private final String CPF = "08010383597";
    private final String CEP = "41200150";
    private final BigDecimal VALUE = new BigDecimal("30");
    private static final Long ID  = 1L;

    private static final String URL = "/pedidos";
    private static final String URL_ID = URL+"/1";
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Test
    void whenCreateOrderReturnSucess() throws Exception {
        var orderDTO = getOrderDTO();
        var orderResponse = getOrderResponse();

        when(orderService.createOrder(orderDTO)).thenReturn(orderResponse);
        objectMapper.registerModule(new JavaTimeModule());
        var request = objectMapper.writeValueAsString(orderDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }
    @Test
    void whenUpdateReturnSucess() throws Exception {
        var orderUpdate = getOrderUpdateDTO();
        var orderResponse = getOrderResponse();

        when(orderService.update(ID, orderUpdate)).thenReturn(orderResponse);

        var request = objectMapper.writeValueAsString(orderUpdate);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void findByIdTest() throws Exception {
        var orderResponse = getOrderResponse();

        when(orderService.findById(any())).thenReturn(orderResponse);
        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(URL_ID)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void findAllTest() throws Exception {
        var pageableResponse = getPageableResponse();

        when(orderService.findall(any(), any())).thenReturn(pageableResponse);

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void deleteTest() throws Exception {
        doNothing().when(orderService).delete(ID);

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete(URL_ID)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }


    private OrderUpdateDTO getOrderUpdateDTO() {
        return OrderUpdateDTO.builder()
                .cpf(CPF)
                .address(getAddressRequestDTO())
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
    private PageableResponse getPageableResponse(){
        return PageableResponse.builder()
                .numberOfElements(1)
                .totalElements(1l)
                .totalPages(1)
                .orders(List.of(getOrder()))
                .build();
    }
}
