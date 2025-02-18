package com.example.springboot.feature_record_transactions.service;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.entity.Products;

public interface ProductsService {
    ApiResponse createProduct(Products Products);

    ApiResponse getAllProducts();
}
