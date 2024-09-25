package com.personal.ecommerce;

import com.personal.ecommerce.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration")
class EcommerceApplicationTests {

	@Autowired
	private ProductController productController;

	@Test
	void contextLoads() {
		assertThat(productController).isNotNull();
	}

}
