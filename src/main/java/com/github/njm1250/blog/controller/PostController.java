package com.github.njm1250.blog.controller;

import com.github.njm1250.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final UserService userService;

    @Autowired
    public PostController(UserService userService) {
        this.userService = userService;
    }


}
