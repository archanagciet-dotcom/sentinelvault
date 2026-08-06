package com.sentinelvault.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.sentinelvault.dto.AuthRequest;
import com.sentinelvault.dto.AuthResponse;
import com.sentinelvault.dto.ChangePasswordRequest;
import com.sentinelvault.dto.RegisterRequest;
import com.sentinelvault.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;

    }

    // Register

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok(
                "User registered successfully"
        );

    }

    // Login

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {

        String token =
                authService.login(request);

        return ResponseEntity.ok(
                new AuthResponse(token)
        );

    }

    // Change Password

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(

            @RequestBody ChangePasswordRequest request,

            Authentication authentication) {

        authService.changePassword(

                authentication.getName(),

                request

        );

        return ResponseEntity.ok(

                "Password changed successfully"

        );

    }

}