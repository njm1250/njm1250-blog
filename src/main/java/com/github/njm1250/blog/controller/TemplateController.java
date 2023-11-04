package com.github.njm1250.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    // 홈 화면
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // 회원가입 화면
    @GetMapping("/signup")
    public String signup() {
        return "register";
    }

    // 로그인 화면
    @GetMapping("/custom-login")
    public String customLogin() {
        return "custom-login";
    }

    // 글 등록 화면
    @GetMapping("/post")
    public String post() {
        return "post";
    }

}
