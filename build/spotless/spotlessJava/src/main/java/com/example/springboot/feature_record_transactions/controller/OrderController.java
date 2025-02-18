package com.example.springboot.feature_record_transactions.controller;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.dtos.OrderRequest;
import com.example.springboot.feature_record_transactions.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * creates a order
     *
     * @param userId
     * @param orderRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(
            @RequestParam(required = true)
                    Long userId, // Ensure userId is passed in the query param
            @RequestBody OrderRequest orderRequest) {
        ApiResponse response = orderService.createOrder(userId, orderRequest);
        return ResponseEntity.ok(response);
    }
}
