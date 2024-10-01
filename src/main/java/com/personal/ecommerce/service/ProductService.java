package com.personal.ecommerce.service;

import com.personal.ecommerce.domain.Product;
import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.infra.exception.ProductNotFoundException;
import com.personal.ecommerce.mapper.ProductMapper;
import com.personal.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getProduct(UUID uuid) {
        Product product = productRepository
                .findById(uuid)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with UUID: " + uuid));
        return ProductMapper.INSTANCE.toDto(product);
    }
}
