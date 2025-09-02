package com.se.Expense_Tracker.controller;

import com.se.Expense_Tracker.dto.request.CategoryRequest;
import com.se.Expense_Tracker.dto.response.CategoryResponse;
import com.se.Expense_Tracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create category
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    // Get categories of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryResponse>> getUserCategories(@PathVariable Long userId) {
        return ResponseEntity.ok(categoryService.getUserCategories(userId));
    }

    // Delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
