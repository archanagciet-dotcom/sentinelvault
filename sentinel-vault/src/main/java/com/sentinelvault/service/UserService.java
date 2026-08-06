package com.sentinelvault.service;

import com.sentinelvault.entity.User;
import com.sentinelvault.repository.UserRepository;
import com.sentinelvault.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    // Register User
    public User register(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }


    // Login User + Generate JWT Token
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                                  .orElse(null);


        if (user != null &&
            passwordEncoder.matches(password, user.getPassword())) {

            return jwtService.generateToken(email);
        }


        return "Invalid email or password";
    }
}