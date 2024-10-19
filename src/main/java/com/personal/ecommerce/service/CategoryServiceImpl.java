package com.personal.ecommerce.service;

import com.personal.ecommerce.domain.Category;
import com.personal.ecommerce.dto.CategoryDto;
import com.personal.ecommerce.infra.exception.CategoryNotFoundException;
import com.personal.ecommerce.mapper.CategoryMapper;
import com.personal.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto getCategoryByName(String categoryName) {
        Category category = this.categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with name: " + categoryName));
        return CategoryMapper.INSTANCE.toDto(category);
    }

}
