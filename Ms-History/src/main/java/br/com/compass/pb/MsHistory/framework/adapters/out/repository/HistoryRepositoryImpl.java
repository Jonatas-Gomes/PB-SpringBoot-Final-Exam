package br.com.compass.pb.MsHistory.framework.adapters.out.repository;

import br.com.compass.pb.MsHistory.application.port.out.HistoryPortOut;
import br.com.compass.pb.MsHistory.domain.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryPortOut {
    private final HistoryJpaRepository repository;
    @Override
    public Page<History> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<History> findByEventDate(LocalDate date, Pageable pageable) {
        return repository.findByEventDate(date, pageable);
    }

    @Override
    public void save(History history) {
        repository.save(history);
    }

    @Override
    public Optional<History> findByIdOrder(Long orderId) {
        return repository.findByIdOrder(orderId);
    }



}
