package br.com.compass.pb.msorder.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class AddressRequestDTO {
    @Pattern(regexp = "^([0-9]{8})+$", message ="Cep deve conter 8 digitos numéricos!")
    @NotBlank
    @NotNull
    @Schema(name = "cep", example = "41200365", type = "String")
    private String cep;
    @Length(max = 6, message = "o número da residência deve ter no máximo 6 digitos númericos!")
    @Pattern(regexp = "^([0-9])+$", message =" campo 'number' deve ser preenchido por digitos númericos!")
    @NotBlank(message = "Campo nome não pode estar em branco!")
    @NotNull(message = "Campo nome não pode ser nulo")
    @Schema(name = "number", example = "4143", type = "String")
    private String number;
}
