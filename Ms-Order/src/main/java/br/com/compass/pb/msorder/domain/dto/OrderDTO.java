package br.com.compass.pb.msorder.domain.dto;


import br.com.compass.pb.msorder.domain.model.Item;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    @Pattern(regexp = "^([0-9]{11})+$", message ="O campo cpf deve ser preenchido por 11 digitos númericos!")
    @CPF()
    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(name = "cpf", example = "93267686512", type = "String")
    private String cpf;
    @NotNull
    @Valid
    @Schema(name = "items")
    private List<ItemDTO> items;
    @NotNull
    @Valid
    @Schema(name = "address")
    private AddressRequestDTO address;

}
