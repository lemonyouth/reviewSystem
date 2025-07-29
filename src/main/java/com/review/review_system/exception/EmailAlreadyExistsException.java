package com.review.review_system.exception;

import org.springframework.context.annotation.Bean;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
