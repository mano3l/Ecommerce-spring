package com.personal.ecommerce.controller;

import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.service.ProductServiceImpl;
import com.personal.ecommerce.validation.ValidUUID;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<Page<ProductDto>> productsByCategorySorted(@PathVariable String categoryName,
                                                                     @RequestParam(defaultValue = "price") String sortBy,
                                                                     @RequestParam(defaultValue = "asc") boolean asc,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size
    ) {
        Sort sort = Sort.by(sortBy);
        return ResponseEntity.ok(this.productServiceImpl.getProductsByCategoryOrderedByPrice(categoryName, sort, page, size, asc));
    }

}
