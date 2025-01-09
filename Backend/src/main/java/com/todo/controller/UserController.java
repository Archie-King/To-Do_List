package com.todo.controller;

import com.todo.model.User;
import com.todo.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger=LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    // User Register
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam(defaultValue = "USER") String roles){
        try{
            userService.registerUser(username, password, email, roles);
            logger.info("User registered successfully: {}", username);
            return "User registered successfully.";
        } catch (Exception e){
            logger.error("User registration failed: {}", e.getMessage());
            return "Registration failed.";
        }
    }


    // Get user info
    @GetMapping("/{username}")
    public User getUser(@PathVariable String username){
        return userService.getUserByUsername(username);
    }


}
