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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        var itemsDTO = orderDTO.getItems();
        List<Item> itemEntities = modelMapper.map(itemsDTO, new TypeToken<List<Item>>(){}.getType());

        AddressDTO addressDTO = getAddress(orderDTO.getAddress().getCep());

        var address = Address.builder()
                .city(addressDTO.getLocalidade())
                .street(addressDTO.getLogradouro())
                .state(addressDTO.getUf())
                .district(addressDTO.getBairro())
                .cep(orderDTO.getAddress().getCep())
                .number(orderDTO.getAddress().getNumber())
                .build();

        order.setItems(itemEntities);
        order.setAddress(address);

        BigDecimal total = new BigDecimal(0);

        List<Item> items = order.getItems();
        /*
        items.forEach(item -> {
            item.setOrder(order);
            item.setCreationDate(LocalDate.now());
            total = total.add(item.getValue());
            if(item.getExpirationDate().isBefore(item.getCreationDate())){
                throw new GenericException(HttpStatus.BAD_REQUEST, "O item não pode expirar antes da data de criação!");
            }
        });*/

        for(Item item: items){
            item.setOrder(order);
            item.setCreationDate(LocalDate.now());
            total = total.add(item.getValue());
            if(item.getExpirationDate().isBefore(item.getCreationDate())){
                throw new GenericException(HttpStatus.BAD_REQUEST, "O item não pode expirar antes da data de criação!");
            }
        }
        order.setTotal(total);
        repository.save(order);



        var messageDTO = MessageOrderDTO.builder()
                .orderId(order.getId())
                .total(order.getTotal())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(messageDTO);

        kafkaProducer.sendOrder(message);

        return new OrderResponse(order);
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
        var order = getOrder(id);
        return new OrderResponse(order);
    }

    @Override
    public OrderResponse update(Long id, OrderUpdateDTO orderDTO) {
        var order = getOrder(id);

        AddressDTO addressDTO = getAddress(orderDTO.getAddress().getCep());

        var address = Address.builder()
                .cep(orderDTO.getAddress().getCep())
                .district(addressDTO.getBairro())
                .state(addressDTO.getUf())
                .city(addressDTO.getLocalidade())
                .number(orderDTO.getAddress().getNumber())
                .street(addressDTO.getLogradouro())
                .build();

        order.setCpf(orderDTO.getCpf());
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

    public Order getOrder(Long id){
       return repository.findById(id)
                .orElseThrow(()-> new GenericException(HttpStatus.BAD_REQUEST, "Não foi possivel localizar um pedido com este id"));
    }

    public AddressDTO getAddress(String cep){
        var addressDTO = viaCepClient.findByCep(cep);

        if(addressDTO.getLocalidade() == null)
            throw new GenericException(HttpStatus.BAD_REQUEST,"Cep inexistente");

        return addressDTO;
    }
}
