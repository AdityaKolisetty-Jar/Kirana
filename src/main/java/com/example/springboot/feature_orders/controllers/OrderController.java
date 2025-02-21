package com.example.springboot.feature_orders.controllers;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_authorization.utils.JwtUtil;
import com.example.springboot.feature_orders.models.OrderRequest;
import com.example.springboot.feature_orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * creates an order
     *
     * @param orderRequest
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(
            @RequestHeader("Authorization") String token, @RequestBody OrderRequest orderRequest) {
        String jwtToken = token.substring(7);
        Long userId = JwtUtil.extractUserId(jwtToken);
        ApiResponse response = orderService.createOrder(userId, orderRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * returns all orders
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders() {
        ApiResponse response = orderService.getAllOrders();
        return ResponseEntity.ok(response);
    }
}
