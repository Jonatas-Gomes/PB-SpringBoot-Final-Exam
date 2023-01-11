package br.com.compass.pb.msorder.domain.dto;


import br.com.compass.pb.msorder.domain.model.Address;
import br.com.compass.pb.msorder.domain.model.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Pattern(regexp = "^([0-9])+$", message ="Digite apenas números!")
    @NotBlank
    @NotNull
    private String number;
    private String cpf;
    private List<Item> items;
    private BigDecimal total;
}
