package com.sentinelvault.controller;

import org.springframework.web.bind.annotation.*;

import com.sentinelvault.entity.User;
import com.sentinelvault.service.UserService;


@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Register
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return userService.register(user);
    }


    // Login
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        return userService.login(
                user.getEmail(),
                user.getPassword()
        );
    }
}