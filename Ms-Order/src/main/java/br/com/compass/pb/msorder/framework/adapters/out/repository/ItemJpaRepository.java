package br.com.compass.pb.msorder.framework.adapters.out.repository;

import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {
    <S extends Order> S save(S entity);
}
