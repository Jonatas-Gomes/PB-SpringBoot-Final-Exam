package br.com.compass.pb.msorder.application.ports.out;

import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;

import java.util.List;

public interface ItemPortOut {
     <S extends Order> S save(S item);

     List<Item> saveAll(Iterable<Item> items);
      List<Item> saveAllAndFlush(Iterable<Item> items);
}
