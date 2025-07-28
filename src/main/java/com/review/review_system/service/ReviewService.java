package com.review.review_system.service;
import com.review.review_system.repository.*;
import com.review.review_system.model.*;
import com.review.review_system.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUser_Id(userId);
    }

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProduct_ProductId(productId);
    }

    public List<Review> getReviewsByUserIdAsc(Long userId) {
        List<Review> reviews = reviewRepository.findByUser_IdOrderByTimeAsc(userId);
        return reviews;
    }

    public List<Review> getReviewsByUserIdDesc(Long userId) {
        return reviewRepository.findByUser_IdOrderByTimeDesc(userId);
    }

    public List<Review> getReviewsByProductIdAsc(Long productId) {
        return reviewRepository.findByProduct_IdOrderByTimeAsc(productId);
    }

    public List<Review> getReviewsByProductIdDesc(Long productId) {
        return reviewRepository.findByProduct_IdOrderByTimeDesc(productId);
    }

    public Review createReview(ReviewRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Purchase purchase = purchaseRepository.findById(request.getPurchaseId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Review review = new Review();
        review.setUser(user);
        review.setPurchase(purchase);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setTime(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    public Review updateReview(Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Purchase purchase = purchaseRepository.findById(request.getPurchaseId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        review.setComment(request.getComment());
        review.setUser(user);
        review.setPurchase(purchase);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setTime(request.getTime());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new RuntimeException("Review not found");
        }
        reviewRepository.deleteById(reviewId);
    }

    // Optionally: Add other methods like createReview, getAllReviews, etc.
}