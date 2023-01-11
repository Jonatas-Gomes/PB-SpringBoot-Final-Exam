package br.com.compass.pb.msorder.framework.adapters.out.repository;

import br.com.compass.pb.msorder.application.ports.out.ItemPortOut;
import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemPortOut {
    private final ItemJpaRepository repository;


    @Override
    public <S extends Order> S save(S item) {
        return repository.save(item);
    }

    @Override
    public List<Item> saveAll(Iterable<Item> items) {
        return repository.saveAll(items);
    }

    @Override
    public List<Item> saveAllAndFlush(Iterable<Item> items) {
        return repository.saveAllAndFlush(items);
    }
}
