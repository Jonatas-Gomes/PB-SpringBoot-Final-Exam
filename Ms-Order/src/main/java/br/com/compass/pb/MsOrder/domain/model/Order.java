package br.com.compass.pb.MsOrder.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CPF
    @Column(nullable = false)
    private String cpf;
    @OneToMany(mappedBy = "orders")
    private Set<Address> items;
    @Column(nullable = false)
    private BigDecimal total;
    @OneToOne
    private Address address;




}
