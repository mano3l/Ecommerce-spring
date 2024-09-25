package com.personal.ecommerce.dto;

import com.personal.ecommerce.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.personal.ecommerce.domain.Product}
 */
@Value
public class ProductDto implements Serializable {

    @NotNull
    UUID id;

    @Size(max = 100)
    @NotBlank
    String name;

    @Size(max = 255)
    String description;

    @NotNull
    @PositiveOrZero
    Double price;

    @NotNull
    @PositiveOrZero
    Integer stockQuantity;

    @NotNull
    Category category;

}