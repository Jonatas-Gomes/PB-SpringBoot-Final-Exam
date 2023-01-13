package br.com.compass.pb.msorder.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class ItemResponse {
    private String name;
    private String description;
    private BigDecimal value;
    private LocalDate creationDate;
    private LocalDate expirationDate;
}
