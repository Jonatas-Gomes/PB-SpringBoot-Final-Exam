package br.com.compass.pb.msorder.domain.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String name;
    private String description;
    private BigDecimal value;
    private LocalDate creationDate;
    private LocalDate expirationDate;
}
