package br.com.compass.pb.msorder.application.service;

import br.com.compass.pb.msorder.application.ports.in.ItemUseCase;
import br.com.compass.pb.msorder.application.ports.out.ItemPortOut;
import br.com.compass.pb.msorder.domain.dto.ItemDTO;
import br.com.compass.pb.msorder.domain.dto.ItemResponse;
import br.com.compass.pb.msorder.framework.exception.GenericException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService implements ItemUseCase {
    private final ItemPortOut portOut;
    private final ModelMapper modelMapper;
    @Override
    public ItemResponse pachItem(Long id, ItemDTO itemDTO) {
        var item = portOut.findById(id)
                .orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Não foi possível localizar um item com este id!"));

        if(itemDTO.getExpirationDate().isBefore(item.getCreationDate()))
            throw new GenericException(HttpStatus.BAD_REQUEST, "data de expiração não pode ser anterior a data de criação!");

        item.setExpirationDate(itemDTO.getExpirationDate());
        item.setValue(itemDTO.getValue());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        portOut.save(item);

        return modelMapper.map(item, ItemResponse.class);
    }
}
