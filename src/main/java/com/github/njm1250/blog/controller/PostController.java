package com.github.njm1250.blog.controller;

import com.github.njm1250.blog.dto.CommentDto;
import com.github.njm1250.blog.dto.PostDto;
import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.entity.Post;
import com.github.njm1250.blog.entity.User;
import com.github.njm1250.blog.repository.PostRepository;
import com.github.njm1250.blog.service.Impl.UserServiceImpl;
import com.github.njm1250.blog.service.PostService;
import com.github.njm1250.blog.service.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/blog")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 블로그 관리자가 작성한 글 조회
    @GetMapping("/getPosts")
    public ResponseEntity<List<PostDto>> getPosts() {
        List<PostDto> postDtos = postService.getPostDtosByAdmin();
        return ResponseEntity.ok(postDtos);
    }

    // 댓글 등록
    @PostMapping("/comment")
    public ResponseEntity<String> createComment(@Valid @RequestBody CommentDto commentDto, HttpSession session) {
        try {
            UserDto user = (UserDto) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
            }
            postService.createComment(commentDto, user);
            return ResponseEntity.ok("Comment created successfully");

        } catch (Exception e) {
            logger.error("Error while creating comment");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the comment");
        }
    }

    // 글 등록
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@Valid @RequestBody PostDto postDto, HttpSession session) {
        try {
            UserDto user = (UserDto) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
            }
            postService.createPost(postDto, user);
            return ResponseEntity.ok("Post created successfully");
        } catch (Exception e) {
            logger.error("Error while creating post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the post");
        }
    }
}
