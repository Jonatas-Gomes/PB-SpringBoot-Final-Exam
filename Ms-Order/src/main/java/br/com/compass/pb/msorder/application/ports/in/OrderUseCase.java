package br.com.compass.pb.msorder.application.ports.in;

import br.com.compass.pb.msorder.domain.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderUseCase {

    OrderResponse createOrder(OrderDTO orderDTO) throws JsonProcessingException;

    PageableResponse findall(String cpf, Pageable Pageable);

    OrderResponse findById(Long id);

    OrderResponse update(Long id, OrderUpdateDTO orderDTO);

    void delete(Long id);
}
