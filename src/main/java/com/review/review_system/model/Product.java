package com.review.review_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private float price;

    public Product(Long productId, String productName, float price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public Product() {

    }

}
