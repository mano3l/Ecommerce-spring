package com.personal.ecommerce.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
public class Order {

    @Id
    @GeneratedValue
    private UUID id;
    private Date date;
    private String status;
    private Double total_amount;

    @OneToOne
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
