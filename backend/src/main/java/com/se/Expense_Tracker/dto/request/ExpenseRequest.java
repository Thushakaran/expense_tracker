package com.se.Expense_Tracker.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseRequest {
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "User ID is required")
    private Long userId;
}
