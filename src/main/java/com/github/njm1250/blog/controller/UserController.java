package com.github.njm1250.blog.controller;

import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/users/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        //TODO user form validation 해야함
        userService.registerUser(userDto);
        return ResponseEntity.status(201).body("User created successfully");
    }
}
