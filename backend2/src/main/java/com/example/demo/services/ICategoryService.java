package com.example.demo.services;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.models.Category;

import java.util.List;

public interface ICategoryService {
//    Category createCategory(Category category);
    Category createCategory(CategoryDTO category);

    Category getCategory(long id);

    List<Category> getAllCategories();

//    Category updateCategory(long id, Category category);
    Category updateCategory(long id, CategoryDTO category);

    void deleteCategory(long id);
}