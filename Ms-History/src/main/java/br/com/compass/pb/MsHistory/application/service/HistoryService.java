package br.com.compass.pb.MsHistory.application.service;

import br.com.compass.pb.MsHistory.application.ports.in.HistoryUseCase;
import br.com.compass.pb.MsHistory.application.ports.out.HistoryPortOut;
import br.com.compass.pb.MsHistory.domain.dto.HistoryDTO;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import br.com.compass.pb.MsHistory.domain.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HistoryService implements HistoryUseCase {
    private final HistoryPortOut portOut;

    @Override
    public PageableResponse findAll(LocalDate date, Pageable pageable) {
        Page<History> histories = date == null ?
                portOut.findAll(pageable) :
                portOut.findByEventDate(date, pageable);
        if (histories.isEmpty()) {
            throw new RuntimeException("Não foram encontrados resultados para esta pesquisa!");
        }

        return PageableResponse.builder()
                .numberOfElements(histories.getNumberOfElements())
                .totalElements(histories.getTotalElements())
                .totalPages(histories.getTotalPages())
                .histories(histories.getContent())
                .build();
    }
}