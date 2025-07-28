package com.review.review_system.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ReviewRequest {
    @Getter
    @Setter
    private Long Id;
    @Getter
    @Setter
    private Long purchaseId;
    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private int rating;
    @Getter
    @Setter
    private String comment;
    @Getter
    @Setter
    private LocalDateTime time;

    public void CreateReviewRequest() {}
}