package br.com.compass.pb.msorder.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDTO {
    @Pattern(regexp = "^([0-9]{11})+$", message ="O campo cpf deve ser preenchido por 11 digitos númericos!")
    @CPF()
    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(name = "cpf", example = "93267686512", type = "String")
    private String cpf;

    @NotNull
    @Valid
    @Schema(name = "address")
    private AddressRequestDTO address;

}

