package com.personal.ecommerce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID ID;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 100)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 8, max = 50)
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Size(max = 100)
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Payment> payment;

}
