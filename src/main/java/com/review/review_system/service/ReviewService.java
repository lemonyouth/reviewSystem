package com.review.review_system.service;
import com.review.review_system.repository.*;
import com.review.review_system.model.*;
import com.review.review_system.DTO.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private fileStorageService fileStorageService;

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
    }

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
    @Transactional
    public Review addReviewWithImages(Review review, List<MultipartFile> images) throws IOException {
        reviewRepository.save(review);

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String storedFileName = fileStorageService.store(image);
                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setFileName(storedFileName);
                reviewImage.setFileUrl("/uploads/reviews/" + storedFileName);
                reviewImage.setReview(review);
                review.addImage(reviewImage);
                reviewImageRepository.save(reviewImage);
            }
        }
        return reviewRepository.save(review);
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

    public Review appendReview(Long reviewId, String comment) {
        Review  review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (review.getAppendContent() != null && !review.getAppendContent().isEmpty()) {
            throw new RuntimeException("You have already appended extra review");
        }
        review.setAppendContent(comment);
        return reviewRepository.save(review);
    }

    // Optionally: Add other methods like createReview, getAllReviews, etc.
}