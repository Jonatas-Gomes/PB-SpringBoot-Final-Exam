package br.com.compass.pb.MsHistory.framework.adapters.in.rest;

import br.com.compass.pb.MsHistory.application.port.in.HistoryUseCase;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    private final HistoryUseCase useCase;
    @GetMapping
    public ResponseEntity<PageableResponse> findAllHistories(@RequestParam(required = false)LocalDate date, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(useCase.findAllHistories(date, pageable));
    }
}
