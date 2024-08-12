package com.example.demo.services;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

//    public CategoryService(CategoryRepository categoryRepository) {         @RequiredArgsConstructor
//        this.categoryRepository = categoryRepository;
//    }

    @Override
//    public Category createCategory(Category category) {
    public Category createCategory(CategoryDTO categoryDTO) {
//        Category newCategory=Category
//                .builder()
//                .name(category.getName())
//                .build();
        Category category=Category.builder().name(categoryDTO.getName()).build(); //chi dung voi 1 truong, n truong lam cach khac
        return categoryRepository.save(category);
//        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found "));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long id, CategoryDTO categoryDTO) {
        Category category1 = getCategory(id);
        category1.setName(categoryDTO.getName());
        categoryRepository.save(category1);
        return category1;
    }

    @Override
    public void deleteCategory(long id) {
        //xóa cứng
        categoryRepository.deleteById(id);
    }
}
