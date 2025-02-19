package com.example.springboot.feature_orders.controller;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_orders.models.OrderRequest;
import com.example.springboot.feature_orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * creates an order
     *
     * @param userId
     * @param orderRequest
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(
            @RequestParam(required = true) Long userId, @RequestBody OrderRequest orderRequest) {
        ApiResponse response = orderService.createOrder(userId, orderRequest);
        return ResponseEntity.ok(response);
    }
}
