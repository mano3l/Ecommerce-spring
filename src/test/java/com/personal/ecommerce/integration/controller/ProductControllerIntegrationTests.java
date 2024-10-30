package com.personal.ecommerce.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.ecommerce.TestCategory;
import com.personal.ecommerce.domain.Category;
import com.personal.ecommerce.domain.Product;
import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.mapper.ProductMapper;
import com.personal.ecommerce.repository.CategoryRepository;
import com.personal.ecommerce.repository.ProductRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag(TestCategory.INTEGRATION_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class ProductControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String uri;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port + "/api/v1";
    }

    @BeforeEach
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    @WithMockUser
    void productShouldReturnProduct() throws Exception {

        ProductRepository productRepository = webApplicationContext.getBean(ProductRepository.class);
        CategoryRepository categoryRepository = webApplicationContext.getBean(CategoryRepository.class);

        Category category = Category.builder()
                .name("Category 1")
                .build();
        categoryRepository.save(category);

        Product product = Product.builder()
                .name("Product 1")
                .price(10.0)
                .stockQuantity(10)
                .category(category)
                .build();
        productRepository.save(product);

        String requestUrl = uri + "/product/" + product.getId();

        MvcResult result = this.mockMvc.perform(get(requestUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        ProductDto responseDto = objectMapper.readValue(jsonResponse, ProductDto.class);

        assertThat(responseDto).isEqualTo(ProductMapper.INSTANCE.toDto(product));

        productRepository.delete(product);
        categoryRepository.delete(category);
    }

    @Test
    @WithMockUser
    void productsByCategorySortedShouldReturnProducts() throws Exception {

        ProductRepository productRepository = webApplicationContext.getBean(ProductRepository.class);
        CategoryRepository categoryRepository = webApplicationContext.getBean(CategoryRepository.class);

        Category category = Category.builder()
                .name("Category 1")
                .build();
        categoryRepository.save(category);

        Product product = Product.builder()
                .name("Product 1")
                .price(10.0)
                .stockQuantity(10)
                .category(category)
                .build();
        productRepository.save(product);

        String requestUrl = uri + "/products/" + category.getName() + "?sortBy=price&asc=true&page=0&size=10";

        MvcResult result = this.mockMvc.perform(get(requestUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode contentNode = rootNode.path("content");
        List<ProductDto> productDtos = objectMapper.readValue(contentNode.toString(), new TypeReference<ArrayList<ProductDto>>() {});

        assertThat(productDtos.getFirst()).isEqualTo(ProductMapper.INSTANCE.toDto(product));

        productRepository.delete(product);
        categoryRepository.delete(category);
    }

}
