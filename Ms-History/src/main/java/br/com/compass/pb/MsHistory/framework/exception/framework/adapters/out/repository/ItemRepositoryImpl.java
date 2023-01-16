package br.com.compass.pb.MsHistory.framework.exception.framework.adapters.out.repository;

import br.com.compass.pb.msorder.application.ports.out.ItemPortOut;
import br.com.compass.pb.msorder.domain.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemPortOut {
    private final ItemJpaRepository repository;


    @Override
    public Item save(Item item) {
        return repository.save(item);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return repository.findById(id);
    }
}
