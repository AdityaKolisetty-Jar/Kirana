package com.example.springboot.feature_products.services;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_products.entities.Products;

public interface ProductsService {
    /**
     * creates product
     *
     * @param Products
     * @return
     */
    ApiResponse createProduct(Products Products);

    /**
     * returns all products
     *
     * @return
     */
    ApiResponse getAllProducts();

    /**
     * returns products by id
     *
     * @param id
     * @return
     */
    ApiResponse getProductById(Long id);

    /**
     * updates products by id
     *
     * @param id
     * @param products
     * @return
     */
    ApiResponse updateProduct(Long id, Products products);

    /**
     * deletes products by id
     *
     * @param id
     * @return
     */
    ApiResponse deleteProduct(Long id);
}
