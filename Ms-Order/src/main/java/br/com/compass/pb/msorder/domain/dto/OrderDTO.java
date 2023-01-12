package br.com.compass.pb.msorder.domain.dto;


import br.com.compass.pb.msorder.domain.model.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Pattern(regexp = "^([0-9]{8})+$", message ="Cep deve conter 8 digitos numéricos!")
    @NotBlank
    @NotNull
    private String cep;
    @Length(max = 6, message = "o número da residência deve ter no máximo 6 digitos númericos!")
    @Pattern(regexp = "^([0-9])+$", message =" campo 'number' deve ser preenchido por digitos númericos!")
    @NotBlank
    @NotEmpty
    @NotNull
    private String number;

    @Pattern(regexp = "^([0-9]{11})+$", message ="O campo cpf deve ser preenchido por 11 digitos númericos!")
    @CPF()
    @NotBlank
    @NotEmpty
    @NotNull
    private String cpf;
    @NotNull
    private List<Item> items;
    @NotNull
    private BigDecimal total;
}
