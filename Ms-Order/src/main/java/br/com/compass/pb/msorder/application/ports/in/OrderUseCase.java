package br.com.compass.pb.msorder.application.ports.in;

import br.com.compass.pb.msorder.domain.dto.OrderDTO;

public interface OrderUseCase {
    OrderDTO createOrder(OrderDTO orderDTO);
}
