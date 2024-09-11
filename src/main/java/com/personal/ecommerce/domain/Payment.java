package com.personal.ecommerce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID ID;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Size(max = 50)
    @Column(name = "method", nullable = false)
    private String method;

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}
