package com.personal.ecommerce.unit.misc;

import com.personal.ecommerce.TestCategory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(TestCategory.UNIT_TEST)
@SpringBootTest
@ActiveProfiles("unit")
public class UnitPropertyLoadingTests {

    @Value("${custom.property}")
    private String customProperty;

    @Test
    void shouldLoadCustomProperty() {
        assertEquals("test-value", customProperty);
    }
}
