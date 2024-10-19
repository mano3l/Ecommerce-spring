package com.personal.ecommerce.service;

import com.personal.ecommerce.domain.Category;
import com.personal.ecommerce.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    ProductDto getProduct(UUID uuid);
    Page<ProductDto> getProductsPageByCategory(String categoryName, Pageable p);

}
