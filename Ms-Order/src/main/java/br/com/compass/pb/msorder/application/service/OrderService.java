package br.com.compass.pb.msorder.application.service;

import br.com.compass.pb.msorder.application.ports.in.OrderUseCase;
import br.com.compass.pb.msorder.application.ports.out.OrderPortOut;
import br.com.compass.pb.msorder.domain.dto.*;
import br.com.compass.pb.msorder.domain.model.Address;
import br.com.compass.pb.msorder.domain.model.Item;
import br.com.compass.pb.msorder.domain.model.Order;
import br.com.compass.pb.msorder.framework.exception.GenericException;
import br.com.compass.pb.msorder.framework.kafka.KafkaProducer;
import br.com.compass.pb.msorder.framework.viacep.ViaCepClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    private final ViaCepClient viaCepClient;
    private final KafkaProducer kafkaProducer;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderResponse createOrder(OrderDTO orderDTO) throws JsonProcessingException {
        var order = modelMapper.map(orderDTO, Order.class);

        AddressDTO addressDTO = viaCepClient.findByCep(orderDTO.getCep());
        if(addressDTO.getLocalidade() == null)
            throw new GenericException(HttpStatus.BAD_REQUEST,"Cep inexistente");

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

        var messageDTO = MessageOrderDTO.builder()
                .orderId(order.getId())
                .total(order.getTotal())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(messageDTO);

        kafkaProducer.sendOrder(message);

        var response = new OrderResponse(order);
        return response;
    }

    @Override
    public PageableResponse findall(String cpf, Pageable pageable) {
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

    @Override
    public OrderResponse update(Long id, OrderDTO orderDTO) {
        var order = repository.findById(id)
                .orElseThrow(()-> new GenericException(HttpStatus.BAD_REQUEST, "Não foi possivel localizar um pedido com este id"));

        AddressDTO addressDTO = viaCepClient.findByCep(orderDTO.getCep());
        if(addressDTO.getLocalidade() == null)
            throw new GenericException(HttpStatus.BAD_REQUEST,"Cep inexistente");

        var address = Address.builder()
                .cep(orderDTO.getCep())
                .district(addressDTO.getBairro())
                .state(addressDTO.getUf())
                .city(addressDTO.getLocalidade())
                .number(orderDTO.getNumber())
                .street(addressDTO.getLogradouro())
                .build();
        
        List<Item> items = orderDTO.getItems();
        for(Item item : items){
            item.setOrder(order);
            item.setCreationDate(LocalDate.now());
            if(item.getExpirationDate().isBefore(item.getCreationDate())){
                throw new GenericException(HttpStatus.BAD_REQUEST, "O item não pode expirar antes da data de criação!");
            }
        }

        order.setItems(items);
        order.setCpf(orderDTO.getCpf());
        order.setTotal(orderDTO.getTotal());
        order.setAddress(address);

        repository.save(order);
        return new OrderResponse(order);
    }

    @Override
    public void delete(Long id) {
        if(!repository.findById(id).isPresent())
            throw new GenericException(HttpStatus.BAD_REQUEST, "Não foi possivel localizar um pedido com este id!");
        repository.deleteById(id);
    }
}
