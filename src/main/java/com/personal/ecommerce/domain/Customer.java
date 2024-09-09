package com.personal.ecommerce.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private UUID ID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Payment> payment;

}
