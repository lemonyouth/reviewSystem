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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private float price;

    public Product(Long productId, String productName, float price) {
        this.id = productId;
        this.productName = productName;
        this.price = price;
    }

    public Product() {

    }

}
