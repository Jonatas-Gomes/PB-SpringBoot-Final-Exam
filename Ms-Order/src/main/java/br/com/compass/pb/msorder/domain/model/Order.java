package br.com.compass.pb.msorder.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @CPF
    @Column(nullable = false)
    private String cpf;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Items> items;

    @Column(nullable = false)
    private BigDecimal total;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;




}
