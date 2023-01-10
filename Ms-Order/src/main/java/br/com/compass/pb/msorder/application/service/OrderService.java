package br.com.compass.pb.msorder.application.service;

import br.com.compass.pb.msorder.application.ports.in.OrderUseCase;
import br.com.compass.pb.msorder.application.ports.out.OrderRepositoryPortOut;
import br.com.compass.pb.msorder.domain.dto.OrderDTO;
import br.com.compass.pb.msorder.domain.model.Order;
import br.com.compass.pb.msorder.framework.adapters.out.repository.OrderRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.management.modelmbean.ModelMBean;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {
    private final OrderRepositoryPortOut repository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        repository.save(order);
        System.out.println(orderDTO.getCpf());
        return modelMapper.map(order, OrderDTO.class);
    }
}
