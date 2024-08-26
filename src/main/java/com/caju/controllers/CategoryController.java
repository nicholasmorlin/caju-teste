package com.caju.controllers;

import com.caju.controllers.dto.request.CategoryCreationRequest;
import com.caju.controllers.dto.response.CategoryResponse;
import com.caju.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createCategory(@RequestBody CategoryCreationRequest request) {
        categoryService.createCategory(request);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.findAllCategories();
    }
}
