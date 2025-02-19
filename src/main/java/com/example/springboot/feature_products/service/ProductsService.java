package com.example.springboot.feature_products.service;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_products.entity.Products;

public interface ProductsService {
    ApiResponse createProduct(Products Products);

    ApiResponse getAllProducts();
}
