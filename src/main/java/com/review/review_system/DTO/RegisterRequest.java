package com.review.review_system.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
}
