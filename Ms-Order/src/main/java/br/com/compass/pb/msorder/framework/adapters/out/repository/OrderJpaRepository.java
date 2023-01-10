package br.com.compass.pb.msorder.framework.adapters.out.repository;

import br.com.compass.pb.msorder.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    @Override
    <S extends Order> S save(S entity);

}
