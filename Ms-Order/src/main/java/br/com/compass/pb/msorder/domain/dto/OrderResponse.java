package br.com.compass.pb.msorder.domain.dto;

import br.com.compass.pb.msorder.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
   private Order order;
}
