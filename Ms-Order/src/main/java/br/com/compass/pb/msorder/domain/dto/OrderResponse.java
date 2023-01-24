package br.com.compass.pb.msorder.domain.dto;

import br.com.compass.pb.msorder.domain.model.Order;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
   private Order order;
}
