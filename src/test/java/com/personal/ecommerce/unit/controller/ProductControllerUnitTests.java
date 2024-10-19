package com.personal.ecommerce.unit.controller;

import com.personal.ecommerce.TestCategory;
import com.personal.ecommerce.controller.ProductController;
import com.personal.ecommerce.domain.Category;
import com.personal.ecommerce.domain.Product;
import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.mapper.ProductMapper;
import com.personal.ecommerce.service.ProductServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag(TestCategory.UNIT_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
public class ProductControllerUnitTests {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port + "/api/v1";
    }

    @Mock
    private ProductServiceImpl productServiceImpl;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.standaloneSetup(productController);
    }

    @Test
    void productShouldReturnProduct() throws Exception {

        // Given
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
        // When
        when(productServiceImpl.getProduct(any(UUID.class))).thenReturn(productDto);

        // Then
        RestAssuredMockMvc.given()
                .when()
                .get(uri + "/product/" + product.getId())
                .then()
                .statusCode(200);
    }

    @Test
    void productsByCategoryShouldReturnProducts() throws Exception {

        // Given
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
        // When
        when(productServiceImpl.getProductsPageByCategory(any(String.class), any())).thenReturn(null);

        // Then
        RestAssuredMockMvc.given()
                .when()
                .get(uri + "/products/" + category.getName())
                .then()
                .statusCode(200);
    }

}

