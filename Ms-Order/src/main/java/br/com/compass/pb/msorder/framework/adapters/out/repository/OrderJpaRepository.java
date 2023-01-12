package br.com.compass.pb.msorder.framework.adapters.out.repository;

import br.com.compass.pb.msorder.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    @Override
    <S extends Order> S save(S entity);
    Page<Order> findByCpf(String cpf, Pageable pageable);


}
