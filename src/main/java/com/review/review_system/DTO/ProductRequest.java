package com.review.review_system.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequest {
    private Long productId;
    private String productName;
    private float productPrice;
}
