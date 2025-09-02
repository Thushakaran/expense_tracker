package com.se.Expense_Tracker.service;

import com.se.Expense_Tracker.dto.request.ExpenseRequest;
import com.se.Expense_Tracker.dto.response.ExpenseResponse;
import com.se.Expense_Tracker.entity.Category;
import com.se.Expense_Tracker.entity.Expense;
import com.se.Expense_Tracker.entity.User;
import com.se.Expense_Tracker.repository.CategoryRepository;
import com.se.Expense_Tracker.repository.ExpenseRepository;
import com.se.Expense_Tracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public ExpenseResponse createExpense(ExpenseRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .category(category)
                .user(user)
                .build();

        Expense saved = expenseRepository.save(expense);

        return new ExpenseResponse(
                saved.getId(),
                saved.getAmount(),
                saved.getDescription(),
                saved.getDate(),
                category.getName(),
                user.getUsername()
        );
    }

    // Get expenses by user with pagination
    public Page<ExpenseResponse> getUserExpenses(Long userId, int page, int size) {
        return expenseRepository.findByUserId(userId, PageRequest.of(page, size))
                .map(expense -> new ExpenseResponse(
                        expense.getId(),
                        expense.getAmount(),
                        expense.getDescription(),
                        expense.getDate(),
                        expense.getCategory().getName(),
                        expense.getUser().getUsername()
                ));
    }

    // Delete expense
    public void deleteExpense(Long expenseId) {
        if (!expenseRepository.existsById(expenseId)) {
            throw new RuntimeException("Expense not found");
        }
        expenseRepository.deleteById(expenseId);
    }
}
