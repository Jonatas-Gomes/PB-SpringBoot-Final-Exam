package br.com.compass.pb.MsHistory.domain.dto;

import br.com.compass.pb.MsHistory.domain.model.History;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse {
    private Integer numberOfElements;
    private Long totalElements;
    private Integer totalPages;
    private List<History> histories;
}
