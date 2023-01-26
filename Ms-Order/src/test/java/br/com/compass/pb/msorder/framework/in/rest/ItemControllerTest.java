package br.com.compass.pb.msorder.framework.in.rest;

import br.com.compass.pb.msorder.application.service.ItemService;
import br.com.compass.pb.msorder.domain.dto.ItemDTO;
import br.com.compass.pb.msorder.domain.dto.ItemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {
    @MockBean
    private ItemService itemService;
    @Autowired
    private MockMvc mockMvc;

    @Spy
    private ObjectMapper objectMapper;


    private static final Long ID  = 1L;
    private static final String URL = "/pedidos/itens/1";

    private final BigDecimal VALUE = new BigDecimal("30");

    private final LocalDate DATE = LocalDate.now();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Test
    void patchItemTest() throws Exception {
        var itemDTO = getItemDTO();
        var itemResponse = getItemResponse();
        when(itemService.pachItem(ID, itemDTO)).thenReturn(itemResponse);

        var request = objectMapper.writeValueAsString(itemDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private ItemDTO getItemDTO() {
        return ItemDTO.builder()
                .name("this a name")
                .value(VALUE)
                .description("this a description")
                .expirationDate(LocalDate.of(2023, 02, 25))
                .build();
    }

    private ItemResponse getItemResponse(){
        return ItemResponse.builder()
                .creationDate(DATE)
                .expirationDate(LocalDate.of(2023, 02, 25))
                .description("this is a description")
                .name("name")
                .value(VALUE)
                .build();
    }


}
