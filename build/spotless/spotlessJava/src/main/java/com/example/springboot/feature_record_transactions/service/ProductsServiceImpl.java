package com.example.springboot.feature_record_transactions.service;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.daos.ProductsDao;
import com.example.springboot.feature_record_transactions.entity.Products;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductsDao productsDao;

    public ProductsServiceImpl(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    /**
     * creates a product
     *
     * @param products
     * @return
     */
    @Override
    public ApiResponse createProduct(Products products) {
        Products savedProducts = productsDao.save(products);
        return new ApiResponse(true, null, "Transaction created successfully", null, savedProducts);
    }

    /**
     * returns all products
     *
     * @return
     */
    @Override
    public ApiResponse getAllProducts() {
        List<Products> products = productsDao.findAll();
        return new ApiResponse(true, null, "Transactions retrieved successfully", null, products);
    }
}
