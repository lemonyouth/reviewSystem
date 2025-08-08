package com.review.review_system.controller;
import com.review.review_system.model.ReviewImage;
import com.review.review_system.repository.ReviewImageRepository;
import com.review.review_system.service.ReviewService;
import com.review.review_system.model.Review;
import com.review.review_system.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "desc") String order) {

        List<Review> reviews = order.equals("asc")
                ? reviewService.getReviewsByUserIdAsc(userId)
                : reviewService.getReviewsByUserIdDesc(userId);

        return ResponseEntity.ok(reviews);
    }
    @PostMapping("/{reviewId}/images")
    public ResponseEntity<?> uploadReviewImages(
            @PathVariable Long reviewId,
            @RequestParam("files") List<MultipartFile> files) throws IOException {

        if (files.size() > 5) {
            return ResponseEntity.badRequest().body("You can upload up to 5 images.");
        }

        Review review = reviewService.findById(reviewId);

        Review newReview = reviewService.addReviewWithImages(review, files);

        return ResponseEntity.ok(newReview);
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "desc") String order) {

        List<Review> reviews = order.equals("asc")
                ? reviewService.getReviewsByProductIdAsc(productId)
                : reviewService.getReviewsByProductIdDesc(productId);

        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request) {
        Review saved = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequest request) {
        Review saved = reviewService.updateReview(reviewId, request);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Review> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{reviewId}/append")
    public ResponseEntity<Review> appendReview(@PathVariable Long reviewId, String appendContent) {
        Review saved = reviewService.appendReview(reviewId, appendContent);
        return ResponseEntity.ok(saved);
    }

}