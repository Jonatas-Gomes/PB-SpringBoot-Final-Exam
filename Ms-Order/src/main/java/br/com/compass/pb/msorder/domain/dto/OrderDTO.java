package br.com.compass.pb.msorder.domain.dto;


import br.com.compass.pb.msorder.domain.model.Address;
import br.com.compass.pb.msorder.domain.model.Items;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String cpf;
    private Set<Items> items;
    private BigDecimal total;
    private Address address;
}
