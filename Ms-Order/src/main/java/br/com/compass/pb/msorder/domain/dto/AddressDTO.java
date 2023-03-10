package br.com.compass.pb.msorder.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
}
