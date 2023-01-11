package br.com.compass.pb.msorder.framework.viacep;

import br.com.compass.pb.msorder.domain.dto.AddressDTO;
import br.com.compass.pb.msorder.domain.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface ViaCepClient {
    @GetMapping("{cep}/json")
    AddressDTO findByCep(@PathVariable("cep") String cep);
}
