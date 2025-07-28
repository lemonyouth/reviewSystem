package com.review.review_system.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "users")
public class User {
    @Getter
    @Setter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(unique = true, nullable = false)
    private String username;

    @Setter
    @Getter
    @Column(unique = true, nullable = true)
    private String email;

    @Getter
    @Setter
    @Column(unique = true, nullable = true)
    private String phoneNumber;

    @AssertTrue(message = "At least one of email or phone number must be provided")
    public boolean isEmailOrPhoneProvided() {
        return (email != null && !email.trim().isEmpty()) ||
                (phoneNumber != null && !phoneNumber.trim().isEmpty());
    }

    @Setter
    @Getter
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    public User orElseThrow(Object userNotFound) {
    }

    public String getUsername() {
    }

    // 之后可以扩展字段：email、avatar、role等



}
