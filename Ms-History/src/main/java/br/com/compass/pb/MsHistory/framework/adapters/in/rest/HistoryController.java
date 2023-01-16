package br.com.compass.pb.MsHistory.framework.adapters.in.rest;

import br.com.compass.pb.MsHistory.application.ports.in.HistoryUseCase;
import br.com.compass.pb.MsHistory.domain.dto.HistoryDTO;
import br.com.compass.pb.MsHistory.domain.dto.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryUseCase useCase;

    public ResponseEntity<PageableResponse> findAll(@RequestParam(required = false) LocalDate date, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(useCase.findAll(date, pageable));
    }
}
