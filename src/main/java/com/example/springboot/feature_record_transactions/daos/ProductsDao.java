package com.example.springboot.feature_record_transactions.daos;

import com.example.springboot.feature_record_transactions.entity.Products;
import com.example.springboot.feature_record_transactions.repository.ProductsRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProductsDao {
    private final ProductsRepository productsRepository;

    public ProductsDao(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    /**
     * saves a product
     *
     * @param products
     * @return
     */
    public Products save(Products products) {
        return productsRepository.save(products);
    }

    /**
     * finds all products
     *
     * @return
     */
    public List<Products> findAll() {
        return productsRepository.findAll();
    }
}
