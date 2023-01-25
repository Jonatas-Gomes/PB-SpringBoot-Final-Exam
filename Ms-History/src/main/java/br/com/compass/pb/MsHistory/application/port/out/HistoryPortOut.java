package br.com.compass.pb.MsHistory.application.port.out;

import br.com.compass.pb.MsHistory.domain.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistoryPortOut {
    Page<History> findAll(Pageable pageable);
    Page<History> findByEventDate(LocalDate date, Pageable pageable);

    void save(History history);

    Optional<History> findByIdOrder(Long orderId);

    void updateHistory(Long orderId, BigDecimal total, LocalDate localDate);

    Page<History> findByEventDateBetween(LocalDate inicio, LocalDate fim, Pageable pageable);
}
