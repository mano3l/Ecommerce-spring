package com.personal.ecommerce.controller;

import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.service.ProductServiceImpl;
import com.personal.ecommerce.validation.ValidUUID;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("api/v1")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping("product/{uuid}")
    public ResponseEntity<ProductDto> product(@PathVariable @Valid @ValidUUID String uuid) {
        return ResponseEntity.ok(productServiceImpl.getProduct(UUID.fromString(uuid)));
    }

    @GetMapping("products/{categoryName}")
    public ResponseEntity<Page<ProductDto>> productsByCategory(@PathVariable @Valid String categoryName) {
        Pageable pageable = Pageable.ofSize(10);
        return ResponseEntity.ok(this.productServiceImpl.getProductsPageByCategory(categoryName, pageable));
    }

}
