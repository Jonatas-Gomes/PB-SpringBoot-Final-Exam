package br.com.compass.pb.MsHistory.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
public class HistoryDTO {
    private Long id;
    private Long idOrder;
    private BigDecimal total;
    private LocalDateTime eventDate;
    private String code;
}
