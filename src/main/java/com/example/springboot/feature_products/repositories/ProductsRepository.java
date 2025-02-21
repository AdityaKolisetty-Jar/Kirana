package com.example.springboot.feature_products.repositories;

import com.example.springboot.feature_products.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {}
