package br.com.compass.pb.msorder.application.service;

import br.com.compass.pb.msorder.application.ports.in.OrderUseCase;
import br.com.compass.pb.msorder.application.ports.out.ItemPortOut;
import br.com.compass.pb.msorder.application.ports.out.OrderPortOut;
import br.com.compass.pb.msorder.domain.dto.AddressDTO;
import br.com.compass.pb.msorder.domain.dto.ItemDTO;
import br.com.compass.pb.msorder.domain.dto.OrderDTO;
import br.com.compass.pb.msorder.domain.dto.OrderResponse;
import br.com.compass.pb.msorder.domain.model.Address;
import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;
import br.com.compass.pb.msorder.framework.viacep.ViaCepClient;
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
    private final ViaCepClient viaCepClient;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) {
        var order = modelMapper.map(orderDTO, Order.class);
        AddressDTO addressDTO = viaCepClient.findByCep(orderDTO.getCep());
        var address = Address.builder()
                .city(addressDTO.getLocalidade())
                .street(addressDTO.getLogradouro())
                .state(addressDTO.getUf())
                .district(addressDTO.getBairro())
                .cep(orderDTO.getCep())
                .number(orderDTO.getNumber())
                .build();
        order.setAddress(address);
        repository.save(order);

        List<Item> items = order.getItems();
        items.forEach(item -> {
            item.setOrder(order);
        });
        itemRepository.saveAll(items);


        var orderResponse = new OrderResponse();
        orderResponse.setItens(items);
        orderResponse.setCpf(orderDTO.getCpf());
        orderResponse.setTotal(orderDTO.getTotal());
        return orderResponse;
    }
}
