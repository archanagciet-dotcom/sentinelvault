package com.sentinelvault.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sentinelvault.dto.AuthRequest;
import com.sentinelvault.dto.ChangePasswordRequest;
import com.sentinelvault.dto.RegisterRequest;
import com.sentinelvault.entity.User;
import com.sentinelvault.repository.UserRepository;
import com.sentinelvault.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AuditLogService auditLogService;

    public AuthService(

            UserRepository userRepository,

            PasswordEncoder passwordEncoder,

            JwtUtil jwtUtil,

            AuthenticationManager authenticationManager,

            AuditLogService auditLogService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.auditLogService = auditLogService;
    }

    // Register

    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new RuntimeException(
                    "Email already exists"
            );

        }

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setPassword(

                passwordEncoder.encode(

                        request.getPassword()

                )

        );

        userRepository.save(user);

        auditLogService.logAction(

                request.getEmail(),

                "REGISTER",

                "-"

        );

    }

    // Login

    public String login(AuthRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),

                        request.getPassword()

                )

        );

        String token =

                jwtUtil.generateToken(

                        request.getEmail()

                );

        auditLogService.logAction(

                request.getEmail(),

                "LOGIN",

                "-"

        );

        return token;

    }

    // Change Password

    public void changePassword(

            String email,

            ChangePasswordRequest request) {

        User user = userRepository.findByEmail(email)

                .orElseThrow(() ->

                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(

                request.getOldPassword(),

                user.getPassword())) {

            throw new RuntimeException(

                    "Old password is incorrect"

            );

        }

        user.setPassword(

                passwordEncoder.encode(

                        request.getNewPassword()

                )

        );

        userRepository.save(user);

        auditLogService.logAction(

                email,

                "CHANGE PASSWORD",

                "-"

        );

    }

}