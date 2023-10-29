package com.github.njm1250.blog.controller;

import com.github.njm1250.blog.dto.UserDTO;
import com.github.njm1250.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/users")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        //TODO user form validation 해야함
        userService.registerUser(userDTO);
        return ResponseEntity.status(201).body("User created successfully");
    }
}
