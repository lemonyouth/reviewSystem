package com.review.review_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "purchases")
public class Purchase {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Setter
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime time;

    // getters and setters
    public Purchase(Long productId, Long userId, LocalDateTime time) {
        this.productId = productId;
        this.userId = userId;
        this.time = time;
    }

    public Purchase() {

    }

}
