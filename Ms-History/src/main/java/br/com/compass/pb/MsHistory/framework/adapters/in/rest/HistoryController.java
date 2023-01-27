package br.com.compass.pb.MsHistory.framework.adapters.in.rest;

import br.com.compass.pb.MsHistory.application.port.in.HistoryUseCase;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/historico")
public class HistoryController {

    private final HistoryUseCase useCase;
    @GetMapping
    @Operation(summary = "Buscar todo histórico de pedidos")
    public ResponseEntity<PageableResponse> findAllHistories(@RequestParam(required = false)
                                                                 @DateTimeFormat(pattern = "dd-MM-yyyy") @Schema(example = "23-01-2023") LocalDate inicio,
                                                             @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") @Schema(example = "30-03-2023") LocalDate fim,
                                                             @PageableDefault(sort = "eventDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(useCase.findAllHistories(inicio, fim, pageable));
    }
}
