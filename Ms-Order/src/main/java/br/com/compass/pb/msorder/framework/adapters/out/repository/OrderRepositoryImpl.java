package br.com.compass.pb.msorder.framework.adapters.out.repository;

import br.com.compass.pb.msorder.application.ports.out.OrderPortOut;
import br.com.compass.pb.msorder.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderPortOut {

    private final OrderJpaRepository orderRepository;
    @Override
    public <S extends Order> S save(S order) {
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> findAll(Pageable page) {
        return orderRepository.findAll(page);
    }

    @Override
    public Page<Order> findByCpf(String cpf, Pageable pageable) {
        return orderRepository.findByCpf(cpf, pageable);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }


}
