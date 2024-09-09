package com.personal.ecommerce.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private UUID ID;
    private Integer quantity;
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
