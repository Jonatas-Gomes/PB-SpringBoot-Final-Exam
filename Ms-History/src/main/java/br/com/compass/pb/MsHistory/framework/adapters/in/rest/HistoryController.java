package br.com.compass.pb.MsHistory.framework.adapters.in.rest;

import br.com.compass.pb.MsHistory.application.port.in.HistoryUseCase;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<PageableResponse> findAllHistories(@RequestParam(required = false)
                                                                 @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio,
                                                             @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fim,
                                                             Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(useCase.findAllHistories(inicio, fim, pageable));
    }
}
