package com.sentinelvault.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.sentinelvault.entity.User;
import com.sentinelvault.repository.UserRepository;

@RestController
@RequestMapping("/profile")
@CrossOrigin("*")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<User> getProfile(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Don't expose the password
        user.setPassword(null);

        return ResponseEntity.ok(user);
    }
}