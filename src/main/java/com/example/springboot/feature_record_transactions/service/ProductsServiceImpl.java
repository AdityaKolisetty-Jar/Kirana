package com.example.springboot.feature_record_transactions.service;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.daos.ProductsDao;
import com.example.springboot.feature_record_transactions.entity.Products;
import java.util.List;
import org.springframework.stereotype.Service;

import static com.example.springboot.feature_record_transactions.constants.ProductsConstants.*;

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
        return new ApiResponse(true, null, PRODUCT_CREATED_SUCCESSFULLY, null, savedProducts);
    }

    /**
     * returns all products
     *
     * @return
     */
    @Override
    public ApiResponse getAllProducts() {
        List<Products> products = productsDao.findAll();
        return new ApiResponse(true, null, PRODUCTS_RETRIEVED_SUCCESSFULLY, null, products);
    }
}
