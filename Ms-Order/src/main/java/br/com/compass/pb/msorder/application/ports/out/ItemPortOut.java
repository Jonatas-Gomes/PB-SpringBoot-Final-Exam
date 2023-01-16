package br.com.compass.pb.msorder.application.ports.out;

import br.com.compass.pb.msorder.domain.model.Item;

import java.util.Optional;

public interface ItemPortOut {
     Item save(Item item);
     Optional<Item> findById(Long id);

}
