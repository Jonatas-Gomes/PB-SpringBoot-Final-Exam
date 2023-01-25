package br.com.compass.pb.MsHistory.application.service;

import br.com.compass.pb.MsHistory.application.port.in.HistoryUseCase;
import br.com.compass.pb.MsHistory.application.port.out.HistoryPortOut;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import br.com.compass.pb.MsHistory.domain.model.History;
import br.com.compass.pb.MsHistory.framework.exception.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
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

}
