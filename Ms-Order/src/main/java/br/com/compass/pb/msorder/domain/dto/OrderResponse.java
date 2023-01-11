package br.com.compass.pb.msorder.domain.dto;

import br.com.compass.pb.msorder.domain.model.Address;
import br.com.compass.pb.msorder.domain.model.Item;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class OrderResponse {
    private String cpf;
    private List<Item> itens;
    private BigDecimal total;
    private Address endereco;
}
