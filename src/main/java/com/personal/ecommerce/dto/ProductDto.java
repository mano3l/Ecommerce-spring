package com.personal.ecommerce.dto;

import com.personal.ecommerce.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(stockQuantity, that.stockQuantity) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, stockQuantity, category);
    }
}