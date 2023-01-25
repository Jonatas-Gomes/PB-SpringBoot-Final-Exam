package br.com.compass.pb.MsHistory.framework.in.rest;

import br.com.compass.pb.MsHistory.application.service.HistoryService;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import br.com.compass.pb.MsHistory.domain.model.History;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class HistoryControllerTest {
    @MockBean
    private HistoryService service;

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/histories";

    @Test
    void whenGetOrderReturnSucess() throws Exception {
        var pageableResponse = getPageableResponse();

        when(service.findAllHistories(any(), any(), any())).thenReturn(pageableResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private PageableResponse getPageableResponse() {
        return PageableResponse.builder()
                .totalElements(1l)
                .totalPages(1)
                .numberOfElements(1)
                .histories(getHistories())
                .build();
    }

    private List<History> getHistories() {
        var histories = History.builder()
                .id("#25ab")
                .total(new BigDecimal("30"))
                .idOrder(2l)
                .eventDate(LocalDate.now())
                .build();
        return List.of(histories);
    }
}
