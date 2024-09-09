package com.personal.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private UUID ID;
    private String Name;
    private String Description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
