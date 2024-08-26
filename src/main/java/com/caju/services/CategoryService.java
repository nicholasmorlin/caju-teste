package com.caju.services;

import com.caju.controllers.dto.request.CategoryCreationRequest;
import com.caju.controllers.dto.response.CategoryResponse;
import com.caju.exceptions.DuplicateKeyException;
import com.caju.helpers.CategoryConverter;
import com.caju.model.Category;
import com.caju.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(CategoryCreationRequest request) {

        if (categoryRepository.existsByType(request.categoryName())) {
            throw new DuplicateKeyException("Category already registed");
        }

        Category category = new Category(request.categoryName());
        categoryRepository.save(category);
    }

    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryConverter::toResponse)
                .collect(Collectors.toList());
    }
}
