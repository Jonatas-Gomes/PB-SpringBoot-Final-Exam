package br.com.compass.pb.msorder.application.ports.out;

import br.com.compass.pb.msorder.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderPortOut {
    <S extends Order> S save(S entity);
    Page<Order> findAll (Pageable page);
    Page<Order> findByCpf(String cpf, Pageable pageable);
}
