package com.example.springboot.feature_orders.helpers;

import static com.example.springboot.feature_orders.constants.OrderConstants.*;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_products.entities.Products;
import com.example.springboot.feature_products.repositories.ProductsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class OrderHelper {
    private final ProductsRepository productsRepository;

    public OrderHelper(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    /**
     * validates and sums the amount of the selected products while ordering
     *
     * @param productIds
     * @param targetCurrency
     * @return
     */
    public ApiResponse validateAndProcessProducts(List<Long> productIds, String targetCurrency) {
        double totalAmount = 0;

        for (Long productId : productIds) {
            Optional<Products> productOpt = productsRepository.findById(productId);

            if (productOpt.isEmpty()) {
                return new ApiResponse(
                        false,
                        PRODUCT_NOT_FOUND,
                        PRODUCT_WITH_ID + productId + NOT_FOUND,
                        null,
                        null);
            }

            if (targetCurrency == null || targetCurrency.isEmpty()) {
                return new ApiResponse(false, INVALID_CURRENCY, CURRENCY_IS_REQUIRED, null, null);
            }

            Products product = productOpt.get();
            totalAmount += product.getPrice(); // Sum up product prices

            if (product.getQuantity() > 0) {
                product.setQuantity(product.getQuantity() - 1);
                productsRepository.save(product);
            } else {
                return new ApiResponse(
                        false,
                        OUT_OF_STOCK,
                        PRODUCT_WITH_ID + productId + IS_OUT_OF_STOCK,
                        null,
                        null);
            }
        }

        return new ApiResponse(
                true, PRODUCTS_PROCESSED, ALL_PRODUCTS_PROCESSED_SUCCESSFULLY, null, totalAmount);
    }
}
