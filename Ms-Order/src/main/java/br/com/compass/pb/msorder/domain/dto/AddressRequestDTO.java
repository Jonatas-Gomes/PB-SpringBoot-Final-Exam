package br.com.compass.pb.msorder.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AddressRequestDTO {
    @Pattern(regexp = "^([0-9]{8})+$", message ="Cep deve conter 8 digitos numéricos!")
    @NotBlank
    @NotNull
    private String cep;
    @Length(max = 6, message = "o número da residência deve ter no máximo 6 digitos númericos!")
    @Pattern(regexp = "^([0-9])+$", message =" campo 'number' deve ser preenchido por digitos númericos!")
    @NotBlank(message = "Campo nome não pode estar em branco!")
    @NotNull(message = "Campo nome não pode ser nulo")
    private String number;
}
