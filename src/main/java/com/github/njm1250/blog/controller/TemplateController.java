package com.github.njm1250.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/post")
    public String post() {
        return "post";
    }

}
