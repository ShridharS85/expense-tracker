package com.expense.backend.controller;

import com.expense.backend.dto.*;
import com.expense.backend.entity.User;
import com.expense.backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dashboardService.getDashboard(user));
    }

    @GetMapping("/charts/monthly-expenses")
    public ResponseEntity<List<MonthlyExpensesResponse>> getMonthlyExpenses(
            @RequestParam(defaultValue = "6") int months,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dashboardService.getMonthlyExpenses(user, months));
    }

    @GetMapping("/charts/category-distribution")
    public ResponseEntity<List<CategoryDistributionResponse>> getCategoryDistribution(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dashboardService.getCategoryDistribution(user));
    }

    @GetMapping("/charts/daily-spending")
    public ResponseEntity<List<DailySpendingResponse>> getDailySpending(
            @RequestParam(defaultValue = "30") int days,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dashboardService.getDailySpending(user, days));
    }
}