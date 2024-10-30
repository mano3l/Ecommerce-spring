package com.personal.ecommerce.unit.infra;

import com.personal.ecommerce.TestCategory;
import com.personal.ecommerce.infra.ApiError;
import com.personal.ecommerce.infra.GlobalExceptionHandler;
import com.personal.ecommerce.infra.exception.ProductNotFoundException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Tag(TestCategory.UNIT_TEST)
@SpringBootTest
@ActiveProfiles("unit")
public class GlobalExceptionHandlerUnitTests {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void shouldReturnResponseEntityOfProductNotFoundException() {
        ResponseEntity<ApiError> response =
                globalExceptionHandler.handleProductNotFoundException(new ProductNotFoundException("Product not found"));

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("Product Not Found");
    }

}
