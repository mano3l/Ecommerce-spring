package com.personal.ecommerce.service;

import com.personal.ecommerce.dto.CategoryDto;

public interface CategoryService {

    CategoryDto getCategoryByName(String categoryName);

}
