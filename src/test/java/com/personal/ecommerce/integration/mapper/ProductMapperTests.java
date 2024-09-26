package com.personal.ecommerce.integration.mapper;

import com.personal.ecommerce.TestCategory;
import com.personal.ecommerce.domain.Category;
import com.personal.ecommerce.domain.Product;
import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.mapper.ProductMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag(TestCategory.INTEGRATION_TEST)
public class ProductMapperTests {

    @Test
    void shouldMapProductsToProductDto() {

        // given
        Category category = Category.builder()
                .id(UUID.randomUUID())
                .name("Automotivos")
                .description("Todo tipo de produto automotivo")
                .build();

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name("Pneu")
                .description("Pneu dunlop 205/60R16")
                .category(category)
                .stockQuantity(12)
                .price(402.90)
                .build();

        // when
        ProductDto productDto = ProductMapper.INSTANCE.toDto(product);

        // then
        assertThat(productDto.getId()).isEqualTo(product.getId());
        assertThat(productDto.getName()).isEqualTo("Pneu");
        assertThat(productDto.getDescription()).isEqualTo("Pneu dunlop 205/60R16");
        assertThat(productDto.getCategory()).isEqualTo(category);
        assertThat(productDto.getStockQuantity()).isEqualTo(12);
        assertThat(productDto.getPrice()).isEqualTo(402.90);

    }

}
