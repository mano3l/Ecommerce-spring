package com.personal.ecommerce.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private UUID ID;
    private Double amount;
    private String method;
    private Date date;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
