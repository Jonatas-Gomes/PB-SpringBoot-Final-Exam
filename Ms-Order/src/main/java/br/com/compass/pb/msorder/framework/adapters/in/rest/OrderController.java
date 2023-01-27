package br.com.compass.pb.msorder.framework.adapters.in.rest;

import br.com.compass.pb.msorder.application.ports.in.OrderUseCase;
import br.com.compass.pb.msorder.domain.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class OrderController {
    private final OrderUseCase orderUseCase;
    @PostMapping
    @Operation(summary = "Cadastrar pedidos")
    public ResponseEntity<OrderResponse>createOrder(@RequestBody @Valid OrderDTO orderDTO) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderUseCase.createOrder(orderDTO));
    }
    @GetMapping
    @Operation(summary = "Buscar todos os pedidos")
    public ResponseEntity<PageableResponse> findAll(@RequestParam(required = false )
                                                        @Schema(example = "93267686512") String cpf,
                                                    @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(orderUseCase.findall(cpf, pageable));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar um pedido por id")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderUseCase.findById(id));
    }
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um pedido")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody @Valid OrderUpdateDTO orderDTO){
        return ResponseEntity.status(HttpStatus.OK).body(orderUseCase.update(id, orderDTO));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um pedido")
    public ResponseEntity delete(@PathVariable Long id){
        orderUseCase.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
