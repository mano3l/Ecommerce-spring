package com.personal.ecommerce.controller;

import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.service.ProductService;
import com.personal.ecommerce.validation.ValidUUID;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("product/{uuid}")
    public ResponseEntity<ProductDto> product(@PathVariable @Valid @ValidUUID String uuid) {
        return ResponseEntity.ok(productService.getProduct(UUID.fromString(uuid)));
    }

}
