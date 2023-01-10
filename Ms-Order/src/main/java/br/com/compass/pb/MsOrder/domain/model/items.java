package br.com.compass.pb.MsOrder.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "items")
public class items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name="creation_date")
    private LocalDate creationDate;
    @Column(name="expiration_date")
    private LocalDate expirationDate;
    private BigDecimal value;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

}
