package com.personal.ecommerce.integration.infra;

import com.personal.ecommerce.TestCategory;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.annotation.PostConstruct;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.equalTo;

@Tag(TestCategory.INTEGRATION_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class GlobalExceptionHandlerIntegrationTests {

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
    @WithMockUser(username = "user")
    public void whenInvalidUUID_thenReturnsValidationErrors() throws Exception {
        when().get(uri + "/product/invalid-uuid")
                .then()
                .statusCode(400)
                .body("status", equalTo("BAD_REQUEST"))
                .body("message", equalTo("Validation Error"));
    }

    @Test
    @WithMockUser(username = "user")
    public void whenProductNotFound_thenReturnsProductNotFoundError() throws Exception {
        when().get(uri + "/product/1b9d6bcd-bbfd-4b2d-9b5d-ab8dfbbd4b1e")
                .then()
                .statusCode(404)
                .body("status", equalTo("NOT_FOUND"))
                .body("message", equalTo("Product Not Found"));
    }

    @Test
    @WithMockUser(username = "user")
    public void whenCategoryNotFound_thenReturnsCategoryNotFoundError() throws Exception {
        when().get(uri + "/products/invalid-category?sortBy=price&asc=true&page=0&size=10")
                .then()
                .statusCode(404)
                .body("status", equalTo("NOT_FOUND"))
                .body("message", equalTo("Category Not Found"));
    }
}