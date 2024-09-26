package com.personal.ecommerce.integration.infra;

import com.personal.ecommerce.TestCategory;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;

@Tag(TestCategory.INTEGRATION_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class ExceptionHandlerTests {

    @LocalServerPort
    private int port;

    private String uri;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @BeforeEach
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void whenIllegalArgumentException_thenBadRequest() {
        when().get(uri + "/api/product/--------")
                .then()
                .statusCode(400);
    }

}
