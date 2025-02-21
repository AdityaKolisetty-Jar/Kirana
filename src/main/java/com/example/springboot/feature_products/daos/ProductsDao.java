package com.example.springboot.feature_products.daos;

import com.example.springboot.feature_products.entities.Products;
import com.example.springboot.feature_products.repositories.ProductsRepository;
import java.util.List;
import java.util.Optional;
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

    /**
     * finds product by id
     *
     * @param id
     * @return
     */
    public Optional<Products> findById(Long id) {
        return productsRepository.findById(id);
    }

    /**
     * deletes product by id
     *
     * @param id
     */
    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }
}
