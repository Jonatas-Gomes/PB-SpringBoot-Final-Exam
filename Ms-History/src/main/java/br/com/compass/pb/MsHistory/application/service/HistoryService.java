package br.com.compass.pb.MsHistory.application.service;

import br.com.compass.pb.MsHistory.application.port.in.HistoryUseCase;
import br.com.compass.pb.MsHistory.application.port.out.HistoryPortOut;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import br.com.compass.pb.MsHistory.domain.model.History;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HistoryService implements HistoryUseCase {

    private final HistoryPortOut portOut;
    private final ModelMapper mapper;
    @Override
    public PageableResponse findAllHistories(LocalDate date, Pageable pageable) {
        Page<History> histories = date == null?
                portOut.findAll(pageable):
                portOut.findByEventDate(date, pageable);
        if(histories.isEmpty()){
            throw new RuntimeException("Não há pedidos nesta data!");
        }
        return PageableResponse.builder()
                .totalElements(histories.getTotalElements())
                .numberOfElements(histories.getNumberOfElements())
                .totalPages(histories.getTotalPages())
                .histories(histories.getContent())
                .build();
    }

}
