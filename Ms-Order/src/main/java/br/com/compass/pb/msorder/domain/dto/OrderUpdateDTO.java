package br.com.compass.pb.msorder.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
@Getter
@Setter
@Builder
public class OrderUpdateDTO {
    @Pattern(regexp = "^([0-9]{11})+$", message ="O campo cpf deve ser preenchido por 11 digitos númericos!")
    @CPF()
    @NotBlank
    @NotEmpty
    @NotNull
    private String cpf;

    @NotNull
    @Valid
    private AddressRequestDTO address;

}

