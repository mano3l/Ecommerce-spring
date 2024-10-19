package com.personal.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.personal.ecommerce.domain.Category}
 */
@Value
public class CategoryDto implements Serializable {
    UUID id;
    @Size(max = 50)
    @NotBlank
    String name;
    @Size(max = 255)
    String description;
}