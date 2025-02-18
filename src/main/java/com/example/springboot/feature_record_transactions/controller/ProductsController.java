package com.example.springboot.feature_record_transactions.controller;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.entity.Products;
import com.example.springboot.feature_record_transactions.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    /**
     * creates a product
     *
     * @param products
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody Products products) {
        ApiResponse response = productsService.createProduct(products);
        return ResponseEntity.ok(response);
    }

    /**
     * returns all products
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        ApiResponse response = productsService.getAllProducts();
        return ResponseEntity.ok(response);
    }
}
