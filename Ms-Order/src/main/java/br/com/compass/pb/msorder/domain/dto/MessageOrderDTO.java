package br.com.compass.pb.msorder.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageOrderDTO {
    private Long orderId;
    private BigDecimal total;
}
