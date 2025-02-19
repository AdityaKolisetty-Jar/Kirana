package com.example.springboot.feature_orders.service;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_orders.models.OrderRequest;

public interface OrderService {
    ApiResponse createOrder(Long userId, OrderRequest orderRequest);
}
