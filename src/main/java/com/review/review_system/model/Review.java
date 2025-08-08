package com.review.review_system.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "idx_review_user", columnList = "user_id"),
        @Index(name = "idx_review_purchase", columnList = "purchase_id")
})
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
    @Column(nullable = true)
    private String appendContent;


    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime time;

    @Getter
    @Setter
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> images = new ArrayList<>();

    public void addImage(ReviewImage image) {
        images.add(image);
        image.setReview(this); // maintain bidirectional link
    }

    public void removeImage(ReviewImage image) {
        images.remove(image);
        image.setReview(null); // break bidirectional link
    }



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
