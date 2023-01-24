package br.com.compass.pb.MsHistory.framework.adapters.out.repository;

import br.com.compass.pb.MsHistory.application.port.out.HistoryPortOut;
import br.com.compass.pb.MsHistory.domain.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryPortOut {
    private final HistoryJpaRepository repository;
    private final MongoTemplate mongoTemplate;
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

    @Override
    public void updateHistory(Long orderId, BigDecimal total, LocalDate date) {
        Query query = new Query().addCriteria(Criteria.where("id_order").is(orderId));
        Update update = new Update().set("total", total).set("event_date", date);
        mongoTemplate.update(History.class).matching(query).apply(update).first();
    }


}
