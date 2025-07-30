package com.review.review_system.service;

import com.review.review_system.DTO.AuthResponse;
import com.review.review_system.DTO.*;
import com.review.review_system.exception.EmailAlreadyExistsException;
import com.review.review_system.model.User;
import com.review.review_system.repository.UserRepository;
import com.review.review_system.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.review.review_system.security.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        else if (request.getEmail() != null && userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        else if (request.getPhoneNumber() != null && userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already exists");
        }
        else if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new RuntimeException("Password length less than 6 characters");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        userRepository.save(user);
    }


    public JwtResponse login(LoginRequest request) {
        String loginIdentifier = null;

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            loginIdentifier = request.getUsername();
        } else if (request.getEmail() != null && !request.getEmail().isBlank()) {
            loginIdentifier = request.getEmail();
        } else if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            loginIdentifier = request.getPhoneNumber();
        } else {
            throw new UsernameNotFoundException("No valid login identifier provided.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginIdentifier, request.getPassword())
        );



        // Load user from DB to include extra info if needed

        Optional<User> userOpt = Optional.empty();

        if (request.getUsername() != null) {
            userOpt = userRepository.findByUsername(request.getUsername());
        } else if (request.getEmail() != null) {
            userOpt = userRepository.findByEmail(request.getEmail());
        } else if (request.getPhoneNumber() != null) {
            userOpt = userRepository.findByPhoneNumber(request.getPhoneNumber());
        }

        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found"));


        String jwt = jwtUtil.generateToken(user);
        return new JwtResponse(jwt);
    }

}
