package com.review.review_system.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.review.review_system.DTO.ProductRequest;
import com.review.review_system.model.Product;
import com.review.review_system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setPrice(request.getProductPrice());
        return productRepository.save(product);

    }
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
