package br.com.compass.pb.msorder.framework.adapters.in.rest;

import br.com.compass.pb.msorder.application.ports.in.OrderUseCase;
import br.com.compass.pb.msorder.domain.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class OrderController {
    private final OrderUseCase orderUseCase;
    @PostMapping
    public ResponseEntity<OrderDTO>createOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderUseCase.createOrder(orderDTO));
    }
}
