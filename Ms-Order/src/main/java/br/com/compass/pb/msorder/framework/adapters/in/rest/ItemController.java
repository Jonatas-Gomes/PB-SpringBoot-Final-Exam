package br.com.compass.pb.msorder.framework.adapters.in.rest;

import br.com.compass.pb.msorder.application.ports.in.ItemUseCase;
import br.com.compass.pb.msorder.domain.dto.ItemDTO;
import br.com.compass.pb.msorder.domain.dto.ItemResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos/itens")
public class ItemController {
    private final ItemUseCase useCase;
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar um item")
    public ResponseEntity<ItemResponse> patchItem(@PathVariable Long id, @RequestBody @Valid ItemDTO itemDTO) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(useCase.pachItem(id, itemDTO));
    }
}
