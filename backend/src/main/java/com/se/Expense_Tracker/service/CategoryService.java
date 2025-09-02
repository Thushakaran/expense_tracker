package com.se.Expense_Tracker.service;

import com.se.Expense_Tracker.dto.request.CategoryRequest;
import com.se.Expense_Tracker.dto.response.CategoryResponse;
import com.se.Expense_Tracker.entity.Category;
import com.se.Expense_Tracker.entity.User;
import com.se.Expense_Tracker.repository.CategoryRepository;
import com.se.Expense_Tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final
    CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // Create category
    public CategoryResponse createCategory(CategoryRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = Category.builder()
                .name(request.getName())
                .user(user)
                .build();

        Category saved = categoryRepository.save(category);

        return new CategoryResponse(saved.getId(), saved.getName(), user.getUsername());
    }

    // Get categories by user
    public List<CategoryResponse> getUserCategories(Long userId) {
        return categoryRepository.findByUserId(userId).stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    // Delete category
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(categoryId);
    }
}
