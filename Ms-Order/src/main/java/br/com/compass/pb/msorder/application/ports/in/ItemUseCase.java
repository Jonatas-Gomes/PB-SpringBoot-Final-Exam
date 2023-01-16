package br.com.compass.pb.msorder.application.ports.in;

import br.com.compass.pb.msorder.domain.dto.ItemDTO;
import br.com.compass.pb.msorder.domain.dto.ItemResponse;

public interface ItemUseCase {
    ItemResponse pachItem(Long id, ItemDTO itemDTO);

}
