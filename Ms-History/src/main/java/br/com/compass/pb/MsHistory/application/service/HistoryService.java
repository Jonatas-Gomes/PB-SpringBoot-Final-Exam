package br.com.compass.pb.MsHistory.application.service;

import br.com.compass.pb.MsHistory.application.port.in.HistoryUseCase;
import br.com.compass.pb.MsHistory.application.port.out.HistoryPortOut;
import br.com.compass.pb.MsHistory.domain.dto.MessageOrderDTO;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import br.com.compass.pb.MsHistory.domain.model.History;
import br.com.compass.pb.MsHistory.framework.exception.GenericException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryService implements HistoryUseCase {

    private final HistoryPortOut portOut;
    @Override
    public PageableResponse findAllHistories(LocalDate inicio, LocalDate fim, Pageable pageable) {
        Page<History> historic = Page.empty();

        if(inicio == null && fim == null){
            historic = portOut.findAll(pageable);

        }else if (inicio != null && fim != null){
            if(fim.isBefore(inicio)){
                throw new GenericException(HttpStatus.BAD_REQUEST, "O parametro final deve ser preenchido com uma data posterior ao parametro de inicio");
            }
            historic = portOut.findByEventDateBetween(inicio, fim, pageable);

        }
        else{
           historic = inicio == null ? portOut.findByEventDate(fim ,pageable)
                   : portOut.findByEventDate(inicio,pageable);
        }
        
        if(historic.isEmpty()){
            throw new GenericException(HttpStatus.BAD_REQUEST, "Não há pedidos nesta data!");
        }
        return PageableResponse.builder()
                .totalElements(historic.getTotalElements())
                .numberOfElements(historic.getNumberOfElements())
                .totalPages(historic.getTotalPages())
                .histories(historic.getContent())
                .build();
    }

    @Override
    public void messageFilter(Long timestamp, MessageOrderDTO message) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
        LocalDate date = time.toLocalDate();

        var history = portOut.findByIdOrder(message.getOrderId());

        if(history.isEmpty()){
            saveHistory(date, message);
        }else{
            updateHistory(date, history, message.getTotal());
        }
    }

    private void updateHistory(LocalDate date, Optional<History> history, BigDecimal total) {
        portOut.updateHistory(history.get().getIdOrder(), total, date);
        log.info("history updated!");
    }

    private void saveHistory(LocalDate date, MessageOrderDTO message) {

        var history = History.builder()
                .idOrder(message.getOrderId())
                .total(message.getTotal())
                .eventDate(date)
                .build();

        portOut.save(history);
        log.info("new order historic saved!");
    }

}
