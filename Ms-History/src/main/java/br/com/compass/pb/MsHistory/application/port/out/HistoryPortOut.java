package br.com.compass.pb.MsHistory.application.port.out;

import br.com.compass.pb.MsHistory.domain.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface HistoryPortOut {
    Page<History> findAll(Pageable pageable);
    Page<History> findByEventDate(LocalDate date, Pageable pageable);

}
