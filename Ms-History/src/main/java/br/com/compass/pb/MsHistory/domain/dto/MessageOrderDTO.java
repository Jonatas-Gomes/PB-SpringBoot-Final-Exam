package br.com.compass.pb.MsHistory.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MessageOrderDTO {
    private Long orderId;
    private BigDecimal total;
}
