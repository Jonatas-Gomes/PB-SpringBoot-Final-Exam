package br.com.compass.pb.msorder.framework.adapters.out.repository;

import br.com.compass.pb.msorder.application.ports.out.OrderRepositoryPortOut;
import br.com.compass.pb.msorder.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepositoryPortOut {

    private final OrderJpaRepository orderRepository;
    @Override
    public <S extends Order> S save(S order) {
        return orderRepository.save(order);
    }
}
