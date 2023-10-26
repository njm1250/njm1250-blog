package com.github.njm1250.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping
    public String test() {
        return "test";
    }
}
