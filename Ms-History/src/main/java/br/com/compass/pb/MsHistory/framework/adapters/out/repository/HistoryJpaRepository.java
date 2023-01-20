package br.com.compass.pb.MsHistory.framework.adapters.out.repository;

import br.com.compass.pb.MsHistory.domain.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;

public interface HistoryJpaRepository extends MongoRepository<History, String> {
    Page<History> findByEventDate(LocalDate date, Pageable pageable);
}
