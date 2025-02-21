package com.example.springboot.feature_orders.services;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_orders.models.OrderRequest;

public interface OrderService {

    /**
     * creates an order
     *
     * @param userId
     * @param orderRequest
     * @return
     */
    ApiResponse createOrder(Long userId, OrderRequest orderRequest);

    /**
     * returns all orders
     *
     * @return
     */
    ApiResponse getAllOrders();
}
