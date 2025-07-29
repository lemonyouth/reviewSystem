package com.review.review_system.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "reviews")
public class Review {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", insertable = false, updatable = false)
    private Purchase purchase;
//    @Column(nullable = false)
//    private Long productId;

    @Getter
    @Setter
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private int rating;

    @Getter
    @Setter
    @Column(nullable = true)
    private String comment;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime time;

    public Review(Long id, User user, Purchase purchase, int rating, String comment, LocalDateTime time) {
        this.id = id;
        this.user = user;
        this.purchase = purchase;
        this.rating = rating;
        this.comment = comment;
        this.time = time;
    }

    public Review() {

    }

    public Long getUserId() {
        return user.getId();
    }

    public Long getPurchaseId() {
        return purchase.getId();
    }

}
