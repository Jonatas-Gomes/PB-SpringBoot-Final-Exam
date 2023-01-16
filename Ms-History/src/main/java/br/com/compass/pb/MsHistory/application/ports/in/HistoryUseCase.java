package br.com.compass.pb.MsHistory.application.ports.in;

import br.com.compass.pb.MsHistory.domain.dto.HistoryDTO;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface HistoryUseCase {

    PageableResponse findAll(LocalDate date, Pageable pageable);
}
