package com.caju.helpers;

import com.caju.controllers.dto.response.CategoryResponse;
import com.caju.model.Category;

public class CategoryConverter {

    public static CategoryResponse toResponse(Category account) {
        return new CategoryResponse(
                account.getId(),
                account.getType()
        );
    }
}
