package com.example.springboot.feature_record_transactions.helper;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.entity.Products;
import com.example.springboot.feature_record_transactions.repository.ProductsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class OrderHelper {
    private final ProductsRepository productsRepository;

    public OrderHelper(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public ApiResponse validateAndProcessProducts(List<Long> productIds, String targetCurrency) {
        double totalAmount = 0;

        for (Long productId : productIds) {
            Optional<Products> productOpt = productsRepository.findById(productId);

            if (productOpt.isEmpty()) {
                return new ApiResponse(
                        false,
                        "PRODUCT_NOT_FOUND",
                        "Product with ID " + productId + " not found",
                        null,
                        null);
            }

            if (targetCurrency == null || targetCurrency.isEmpty()) {
                return new ApiResponse(
                        false, "INVALID_CURRENCY", "Currency is required", null, null);
            }

            Products product = productOpt.get();
            totalAmount += product.getPrice(); // Sum up product prices

            // Decrease product quantity
            if (product.getQuantity() > 0) {
                product.setQuantity(product.getQuantity() - 1);
                productsRepository.save(product);
            } else {
                return new ApiResponse(
                        false,
                        "OUT_OF_STOCK",
                        "Product with ID " + productId + " is out of stock",
                        null,
                        null);
            }
        }

        return new ApiResponse(
                true,
                "PRODUCTS_PROCESSED",
                "All products processed successfully",
                null,
                totalAmount);
    }
}
