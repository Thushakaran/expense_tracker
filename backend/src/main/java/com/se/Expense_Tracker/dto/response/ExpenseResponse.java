package com.se.Expense_Tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
    private String categoryName;
    private String username;


}
