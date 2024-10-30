package com.personal.ecommerce.service;

import com.personal.ecommerce.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface ProductService {

    ProductDto getProduct(UUID uuid);
    Page<ProductDto> getProductsPageByCategory(String categoryName, Pageable p);
    Page<ProductDto> getProductsByCategoryOrderedByPrice(String categoryName, Sort sortBy, int page, int size, boolean ascending);

}
