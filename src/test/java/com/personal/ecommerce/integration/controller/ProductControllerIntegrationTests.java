package com.personal.ecommerce.integration.controller;

import com.personal.ecommerce.TestCategory;
import com.personal.ecommerce.domain.Category;
import com.personal.ecommerce.domain.Product;
import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.repository.CategoryRepository;
import com.personal.ecommerce.repository.ProductRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;

@Tag(TestCategory.INTEGRATION_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class ProductControllerIntegrationTests {

    @LocalServerPort
    private int port;

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

        // Given
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

        // When
        when().get(uri + "/product/" + product.getId())
                .then()
                .statusCode(200)
                .extract()
                .as(ProductDto.class);

        productRepository.delete(product);
        categoryRepository.delete(category);
    }

    @Test
    @WithMockUser
    void productsByCategoryShouldReturnProducts() throws Exception {

        // Given
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

        // When
        when().get(uri + "/products/" + category.getName())
                .then()
                .statusCode(200);

        productRepository.delete(product);
        categoryRepository.delete(category);
    }

}
