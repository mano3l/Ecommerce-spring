package com.personal.ecommerce.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private UUID ID;
    private String Name;
    private String Description;
    private Double price;
    private Integer Quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
