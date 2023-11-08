package com.github.njm1250.blog.controller;

import com.github.njm1250.blog.dto.PostDto;
import com.github.njm1250.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;

@Controller
public class BlogController {

    private final PostService postService;

    @Autowired
    public BlogController(PostService postService) {
        this.postService = postService;
    }

    // 글 상세 조회 화면
    @GetMapping("/post_detail")
    public String postDetail(@RequestParam(name = "postId") Long postId, Model model) {
        try {
            PostDto postDto = postService.getPostDtoById(postId);
            model.addAttribute("post", postDto);
            return "postDetail";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // errorPage.html 같은 에러 페이지로 리디렉션 할 수 있음
        }
    }

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
