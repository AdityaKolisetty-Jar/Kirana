package com.example.springboot.feature_record_transactions.repository;

import com.example.springboot.feature_record_transactions.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {}
