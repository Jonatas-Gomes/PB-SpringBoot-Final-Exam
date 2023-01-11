package br.com.compass.pb.msorder.application.service;

import br.com.compass.pb.msorder.application.ports.in.OrderUseCase;
import br.com.compass.pb.msorder.application.ports.out.ItemPortOut;
import br.com.compass.pb.msorder.application.ports.out.OrderPortOut;
import br.com.compass.pb.msorder.domain.dto.ItemDTO;
import br.com.compass.pb.msorder.domain.dto.OrderDTO;
import br.com.compass.pb.msorder.domain.dto.OrderResponse;
import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {
    private final OrderPortOut repository;
    private final ModelMapper modelMapper;
    private final ItemPortOut itemRepository;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) {
        var order = modelMapper.map(orderDTO, Order.class);
        repository.save(order);

        List<Item> items = order.getItems();
        items.forEach(item -> {
            item.setOrder(order);
        });
        itemRepository.saveAll(items);

        var orderResponse = new OrderResponse();
        orderResponse.setItens(items);
        orderResponse.setCpf(orderDTO.getCpf());
        orderResponse.setEndereco(orderDTO.getAddress());
        orderResponse.setTotal(orderDTO.getTotal());
        return orderResponse;
    }
}
