package br.com.compass.pb.MsHistory.application.service;

import br.com.compass.pb.MsHistory.application.port.out.HistoryPortOut;
import br.com.compass.pb.MsHistory.domain.dto.MessageOrderDTO;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import br.com.compass.pb.MsHistory.domain.model.History;
import br.com.compass.pb.MsHistory.framework.exception.GenericException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.imageio.stream.IIOByteBuffer;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {
    @InjectMocks
    private HistoryService service;
    @Mock
    private HistoryPortOut portOut;

    private final LocalDate INICIO = LocalDate.of(2023, 01, 23 );
    private final LocalDate FINAL = LocalDate.of(2023, 01, 25 );

    private final Long ID_ORDER = 2l;
    private final BigDecimal TOTAL = new BigDecimal("500");

    private final LocalDate DATE = LocalDate.now();

    private LocalDate date;

    private final Long timestamp = 1499070300000l;
    @BeforeEach
    void setUp(){
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
        date = time.toLocalDate();
    }
    @Test
    void shouldSucessFindAllWithoutParam(){
        Pageable pageable = mock(Pageable.class);
        Page< History> page = mock(Page.class);
        List<History> histories = getHistories();

        when(page.getContent()).thenReturn(histories);

        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);
        when(page.getTotalPages()).thenReturn(1);

        when(portOut.findAll(pageable)).thenReturn(page);

        var response = service.findAllHistories(null, null, pageable);

        Assertions.assertEquals(1, response.getNumberOfElements());
        Assertions.assertEquals(1L, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());

        Assertions.assertEquals(response.getHistories().get(0).getId(), histories.get(0).getId());
    }
    @Test
    void shouldSucessFindAllWithParamInicioAndFinal(){
        Pageable pageable = mock(Pageable.class);
        Page< History> page = mock(Page.class);
        List<History> histories = getHistories();
        when(page.getContent()).thenReturn(histories);

        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);
        when(page.getTotalPages()).thenReturn(1);

        when(portOut.findByEventDateBetween(INICIO, FINAL, pageable)).thenReturn(page);

        var response = service.findAllHistories(INICIO, FINAL ,pageable);

        Assertions.assertEquals(1, response.getNumberOfElements());
        Assertions.assertEquals(1L, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());

        Assertions.assertEquals(response.getHistories().get(0).getId(), histories.get(0).getId());
    }
    @Test
    void shouldSucessFindAllWithParamFinalIsNull(){
        Pageable pageable = mock(Pageable.class);
        Page< History> page = mock(Page.class);
        List<History> histories = getHistories();
        when(page.getContent()).thenReturn(histories);

        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);
        when(page.getTotalPages()).thenReturn(1);

        when(portOut.findByEventDate(INICIO, pageable)).thenReturn(page);

        var response = service.findAllHistories(INICIO,null, pageable);

        Assertions.assertEquals(1, response.getNumberOfElements());
        Assertions.assertEquals(1L, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());

        Assertions.assertEquals(response.getHistories().get(0).getId(), histories.get(0).getId());
    }

    @Test
    void shouldSucessFindAllWithParamInicioIsNull(){
        Pageable pageable = mock(Pageable.class);
        Page< History> page = mock(Page.class);
        List<History> histories = getHistories();
        when(page.getContent()).thenReturn(histories);

        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);
        when(page.getTotalPages()).thenReturn(1);

        when(portOut.findByEventDate(FINAL, pageable)).thenReturn(page);

        var response = service.findAllHistories(null,FINAL, pageable);

        Assertions.assertEquals(1, response.getNumberOfElements());
        Assertions.assertEquals(1L, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());

        Assertions.assertEquals(response.getHistories().get(0).getId(), histories.get(0).getId());

    }
    @Test
    void throw_ExceptionWhenTryFindAllWhen_FinalisBefore_Inicio(){
        Pageable pageable = mock(Pageable.class);
        Page< History> page = mock(Page.class);
        var inicio = LocalDate.of(2023,01,23);
        var finalDate = LocalDate.of(2023, 01, 20);

        assertThrows(GenericException.class, () -> service.findAllHistories(inicio, finalDate, pageable));
    }
    @Test
    void throw_ExceptionWhenTryFindAllWithDateNonexistentInDb(){
        Pageable pageable = mock(Pageable.class);
        var inexistentDate = LocalDate.of(2023,01,28);

        when(portOut.findByEventDate(inexistentDate, pageable)).thenReturn(Page.empty());

        assertThrows(GenericException.class, () -> service.findAllHistories(inexistentDate, null, pageable));
    }
    @Test
    void shouldSucessWhenUpdateOrderHistoric(){
        var history = getHistory();
        var message = getMessage();

        when(portOut.findByIdOrder(ID_ORDER)).thenReturn(Optional.of(history));

        doNothing().when(portOut).updateHistory(ID_ORDER, TOTAL, date);

        service.messageFilter(timestamp, message);
        verify(portOut).updateHistory(ID_ORDER, TOTAL, date);
    }
    @Test
    void shouldSucessWhenSaveNewOrderHistoric(){
        var message = getMessage();

        when(portOut.findByIdOrder(anyLong())).thenReturn(Optional.empty());

        doNothing().when(portOut).save(any());

        service.messageFilter(timestamp, message);

        verify(portOut).save(any());
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

    private History getHistory() {
        return History.builder()
                .id("#25ab")
                .total(TOTAL)
                .idOrder(2l)
                .eventDate(date)
                .build();
    }

    private MessageOrderDTO getMessage(){
        MessageOrderDTO messageOrderDTO = new MessageOrderDTO();
        messageOrderDTO.setOrderId(ID_ORDER);
        messageOrderDTO.setTotal(TOTAL);
        return messageOrderDTO;
    }
}
