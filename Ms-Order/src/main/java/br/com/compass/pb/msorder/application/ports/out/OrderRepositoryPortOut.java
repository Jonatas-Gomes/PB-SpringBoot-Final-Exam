package br.com.compass.pb.msorder.application.ports.out;

import br.com.compass.pb.msorder.domain.model.Order;

public interface OrderRepositoryPortOut {
    <S extends Order> S save(S entity);
}
