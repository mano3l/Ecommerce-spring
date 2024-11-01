package com.personal.ecommerce.unit.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.ecommerce.TestCategory;
import com.personal.ecommerce.controller.ProductController;
import com.personal.ecommerce.domain.Category;
import com.personal.ecommerce.domain.Product;
import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.mapper.ProductMapper;
import com.personal.ecommerce.service.ProductServiceImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag(TestCategory.UNIT_TEST)
@WebMvcTest(ProductController.class)
@ActiveProfiles("unit")
public class ProductControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceImpl productServiceImpl;

    @Test
    @WithMockUser
    void productShouldReturnProduct() throws Exception {

        Category category = Category.builder()
                .id(UUID.randomUUID())
                .name("Category 1")
                .build();

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .category(category)
                .build();

        ProductDto productDto = ProductMapper.INSTANCE.toDto(product);

        when(productServiceImpl.getProduct(any(UUID.class))).thenReturn(productDto);

        String requestUrl = "/api/v1/product/" + product.getId();

        MvcResult result = this.mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        ProductDto responseDto = objectMapper.readValue(jsonResponse, ProductDto.class);

        assertThat(responseDto).isEqualTo(productDto);
    }

    @Test
    @WithMockUser
    void productsByCategorySortedShouldReturnProducts() throws Exception {

        Category category = Category.builder()
                .id(UUID.randomUUID())
                .name("Category 1")
                .build();

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .category(category)
                .build();

        ProductDto productDto = ProductMapper.INSTANCE.toDto(product);

        when(productServiceImpl.getProductsByCategoryOrderedByPrice(
                any(String.class),
                any(),
                anyInt(),
                anyInt(),
                anyBoolean())).thenReturn(new PageImpl<>(List.of(productDto)));

        String requestUrl = "/api/v1/products/" + category.getName() + "?sortBy=price&asc=true&page=0&size=10";

        MvcResult result = this.mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode contentNode = rootNode.path("content");
        List<ProductDto> productDtos = objectMapper.readValue(contentNode.toString(), new TypeReference<ArrayList<ProductDto>>() {
        });

        assertThat(productDtos.getFirst()).isEqualTo(ProductMapper.INSTANCE.toDto(product));
    }

}