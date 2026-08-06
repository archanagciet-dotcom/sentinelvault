package com.sentinelvault.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sentinelvault.entity.User;
import com.sentinelvault.repository.UserRepository;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                userRepository.findAll()
        );
    }

}