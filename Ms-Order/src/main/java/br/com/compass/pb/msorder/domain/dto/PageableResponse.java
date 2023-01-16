package br.com.compass.pb.msorder.domain.dto;

import br.com.compass.pb.msorder.domain.model.Order;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageableResponse {
    private Integer numberOfElements;
    private Long totalElements;
    private Integer totalPages;

    private List<Order> orders;
}
