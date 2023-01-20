package br.com.compass.pb.MsHistory.application.port.in;

import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface HistoryUseCase {
    PageableResponse findAllHistories(LocalDate date, Pageable pageable);

}
