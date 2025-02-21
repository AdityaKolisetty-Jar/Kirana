package com.example.springboot.feature_products.services;

import static com.example.springboot.feature_products.constants.ProductsConstants.*;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_products.daos.ProductsDao;
import com.example.springboot.feature_products.entities.Products;
import java.util.List;
import java.util.Optional;
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

    /**
     * returns products by id
     *
     * @param id
     * @return
     */
    @Override
    public ApiResponse getProductById(Long id) {
        Optional<Products> products = productsDao.findById(id);
        return new ApiResponse(true, null, PRODUCT_RETRIEVED_BY_ID_SUCCESSFULLY, null, products);
    }

    /**
     * updates products by id
     *
     * @param id
     * @param updatedProducts
     * @return
     */
    public ApiResponse updateProduct(Long id, Products updatedProducts) {
        Optional<Products> productOptional = productsDao.findById(id);
        if (productOptional.isPresent()) {
            Products existProduct = productOptional.get();

            existProduct.setPid(updatedProducts.getPid());
            existProduct.setName(updatedProducts.getName());
            existProduct.setQuantity(updatedProducts.getQuantity());
            existProduct.setPrice(updatedProducts.getPrice());

            Products savedProduct = productsDao.save(existProduct);
            return new ApiResponse(
                    true, null, PRODUCT_STOCK_UPDATED_SUCCESSFULLY, null, updatedProducts);
        } else {
            return new ApiResponse(false, PRODUCT_NOT_FOUND, null, null, null);
        }
    }

    /**
     * deletes products by id
     *
     * @param id
     * @return
     */
    public ApiResponse deleteProduct(Long id) {
        Optional<Products> productOptional = productsDao.findById(id);

        if (productOptional.isPresent()) {
            productsDao.deleteById(id);
            return new ApiResponse(true, null, "PRODUCT_DELETED_SUCCESSFULLY", null, null);
        } else {
            return new ApiResponse(false, "Product not found", "PRODUCT_NOT_FOUND", null, null);
        }
    }
}
