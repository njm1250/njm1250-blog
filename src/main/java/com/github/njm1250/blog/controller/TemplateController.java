package com.github.njm1250.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "register";
    }

    @GetMapping("/custom-login")
    public String customLogin() {
        return "custom-login";
    }

    @GetMapping("/post")
    public String post() {
        return "post";
    }

}
