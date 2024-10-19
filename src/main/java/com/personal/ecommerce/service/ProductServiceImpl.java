package com.personal.ecommerce.service;

import com.personal.ecommerce.domain.Category;
import com.personal.ecommerce.domain.Product;
import com.personal.ecommerce.dto.ProductDto;
import com.personal.ecommerce.infra.exception.ProductNotFoundException;
import com.personal.ecommerce.mapper.ProductMapper;
import com.personal.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              CategoryServiceImpl categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    @Override
    public ProductDto getProduct(UUID uuid) {
        Product product = productRepository
                .findById(uuid)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with UUID: " + uuid));
        return ProductMapper.INSTANCE.toDto(product);
    }

    @Override
    public Page<ProductDto> getProductsPageByCategory(String categoryName, Pageable p) {
        UUID categoryId = this.categoryService.getCategoryByName(categoryName).getId();
        Page<Product> productPage = this.productRepository.findByCategory_Id(categoryId, p);
        return productPage.map(productMapper::toDto);
    }

}
