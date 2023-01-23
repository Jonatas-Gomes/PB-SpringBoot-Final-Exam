package br.com.compass.pb.msorder.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class ItemDTO {
    @NotBlank
    @NotNull
    @Pattern(regexp = "^([a-zA-ZãÃéÉíÍóÓêÊôÔáÁ\s])+$", message =
            "O campo nome deve ser composto apenas por letras e não deve estar em branco!")
    private String name;

    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;
    @NotNull
    private BigDecimal value;
}
