package br.com.compass.pb.msorder.application.ports.in;

import br.com.compass.pb.msorder.domain.dto.OrderDTO;
import br.com.compass.pb.msorder.domain.dto.OrderResponse;
import br.com.compass.pb.msorder.domain.dto.PageableResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;

public interface OrderUseCase {
    OrderResponse createOrder(OrderDTO orderDTO) throws JsonProcessingException;

    PageableResponse findall(String cpf, Pageable Pageable);

    OrderResponse findById(Long id);

    OrderResponse update(Long id, OrderDTO orderDTO);

    void delete(Long id);
}
