package br.com.compass.pb.msorder.application.service;

import br.com.compass.pb.msorder.application.ports.in.OrderUseCase;
import br.com.compass.pb.msorder.application.ports.out.ItemPortOut;
import br.com.compass.pb.msorder.application.ports.out.OrderPortOut;
import br.com.compass.pb.msorder.domain.dto.AddressDTO;
import br.com.compass.pb.msorder.domain.dto.OrderDTO;
import br.com.compass.pb.msorder.domain.dto.OrderResponse;
import br.com.compass.pb.msorder.domain.dto.PageableResponse;
import br.com.compass.pb.msorder.domain.model.Address;
import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;
import br.com.compass.pb.msorder.framework.exception.GenericException;
import br.com.compass.pb.msorder.framework.viacep.ViaCepClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {
    private final OrderPortOut repository;
    private final ModelMapper modelMapper;
    private final ItemPortOut itemRepository;
    private final ViaCepClient viaCepClient;

    @Override
    @Transactional(rollbackOn = Exception.class)
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
            item.setCreationDate(LocalDate.now());
            if(item.getExpirationDate().isBefore(item.getCreationDate())){
                throw new GenericException(HttpStatus.BAD_REQUEST, "O item não pode expirar antes da data de criação!");
            }
        });
        var response = new OrderResponse(order);
        return response;
    }

    @Override
    public PageableResponse FindAll(String cpf, Pageable pageable) {
        if (cpf != null)
            cpf = cpf.replaceAll(" ", "");
        Page<Order> page = cpf == null || cpf.isEmpty()?
                repository.findAll(pageable):
                repository.findByCpf(cpf,pageable);

        if(page.isEmpty())
            throw new GenericException(HttpStatus.BAD_REQUEST,"Não foi possível localizar pedidos com este cpf");


          return PageableResponse.builder().
                  numberOfElements(page.getNumberOfElements())
                  .totalElements(page.getTotalElements())
                  .totalPages(page.getTotalPages())
                  .orders(page.getContent())
                  .build();

    }

    @Override
    public OrderResponse findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(()-> new GenericException(HttpStatus.BAD_REQUEST, "Não foi possivel localizar um pedido com este id"));
        return new OrderResponse(order);
    }
}
