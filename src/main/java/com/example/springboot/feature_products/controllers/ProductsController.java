package com.example.springboot.feature_products.controllers;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_products.entities.Products;
import com.example.springboot.feature_products.services.ProductsService;
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

    /**
     * returns products by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ApiResponse response = productsService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * updates products by id
     *
     * @param id
     * @param products
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable Long id, @RequestBody Products products) {
        ApiResponse response = productsService.updateProduct(id, products);
        return ResponseEntity.ok(response);
    }

    /**
     * deletes products by id
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        ApiResponse response = productsService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }
}
