package com.review.review_system.repository;

import  com.review.review_system.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUser_Id(Long userId);

    @Query("SELECT r FROM Review r JOIN r.purchase p WHERE p.productId = :productId")
    List<Review> findByProduct_ProductId(@Param("productId") Long productId);

    List<Review> findByUser_IdOrderByTimeAsc(Long userId);
    List<Review> findByUser_IdOrderByTimeDesc(Long userId);

    @Query("SELECT r FROM Review r JOIN r.purchase p WHERE p.productId = :productId ORDER BY r.time ASC")
    List<Review> findByProduct_IdOrderByTimeAsc(@Param("productId") Long productId);

    @Query("SELECT r FROM Review r JOIN r.purchase p WHERE p.productId = :productId ORDER BY r.time DESC")
    List<Review> findByProduct_IdOrderByTimeDesc(@Param("productId") Long productId);

}