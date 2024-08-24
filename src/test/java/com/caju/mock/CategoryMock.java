package com.caju.mock;

import com.caju.model.Category;

public class CategoryMock {

    public static Category generateCategory(Long id, String type) {
        return new Category(id, type);
    }
}
