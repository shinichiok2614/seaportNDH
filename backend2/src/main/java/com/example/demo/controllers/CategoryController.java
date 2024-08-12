package com.example.demo.controllers;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/categories")
//@Validated
@RequiredArgsConstructor
public class CategoryController {
//    @GetMapping("")
//    public ResponseEntity<String> getAllCategories() {
//        return ResponseEntity.ok("getAllCategories");
//    }

    private final CategoryService categoryService;

    @GetMapping("") //http://localhost:8088/api/categories?page=1&limit=10
//    public ResponseEntity<String> getCategories(
    public ResponseEntity<List<Category>> getCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        List<Category> categories = categoryService.getAllCategories();
//        return ResponseEntity.ok("getAllCategories, page =%d, limit =%d");
//        return ResponseEntity.ok(String.format("getAllCategories, page =%d, limit =%d", page, limit));
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCategoryId(@PathVariable Long id) {
        return ResponseEntity.ok("getCategoryId id = " + id);
    }

    //    @PostMapping("")
    //    public ResponseEntity<String> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
    //        return ResponseEntity.ok("insertCategory categoryDTO = "+categoryDTO);
    //    }
    //@Validated kiem tra truoc khi vao insertCategory
    //@Valid
    //@NotEmpty
    //neu k co: body gui len co body la ok, nhung name=null
    //neu co 3 anno: body gui len co body va co name moi ok


    //k dat @Validated de co the truy cap dc vao insertCategory
    //@Valid    //chi hien loi khi da chon raw: {}
    //@NotEmpty(message = "Category's name cannot be empty")
    @PostMapping("")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result) {                         //để hiện được message của @NotEmpty
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            categoryService.createCategory(categoryDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
//        return ResponseEntity.ok("insertCategory categoryDTO = " + categoryDTO);
        return ResponseEntity.ok("Insert category success");
    }


    @PutMapping("/{id}")
//    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO) {  //@RequestBody: gui qua the body cua postman
        categoryService.updateCategory(id, categoryDTO);
//        return ResponseEntity.ok("updateCategory id = " + id);
        return ResponseEntity.ok("Update category successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
//        return ResponseEntity.ok("deleteCategory id = " + id);
        return ResponseEntity.ok("Delete category id = "+id+" successfully");
    }
}
