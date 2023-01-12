package br.com.compass.pb.msorder.framework.adapters.in.rest;

import br.com.compass.pb.msorder.application.ports.in.OrderUseCase;
import br.com.compass.pb.msorder.domain.dto.OrderDTO;
import br.com.compass.pb.msorder.domain.dto.OrderResponse;
import br.com.compass.pb.msorder.domain.dto.PageableResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class OrderController {
    private final OrderUseCase orderUseCase;
    @PostMapping
    public ResponseEntity<OrderResponse>createOrder(@RequestBody @Valid OrderDTO orderDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderUseCase.createOrder(orderDTO));
    }
    @GetMapping
    public ResponseEntity<PageableResponse> findAll(@RequestParam(required = false )String cpf, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(orderUseCase.FindAll(cpf, pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderUseCase.findById(id));
    }
}
