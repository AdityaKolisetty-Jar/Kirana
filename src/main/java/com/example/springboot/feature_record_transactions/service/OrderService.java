package com.example.springboot.feature_record_transactions.service;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.dtos.OrderRequest;

public interface OrderService {
    ApiResponse createOrder(Long userId, OrderRequest orderRequest);
}
