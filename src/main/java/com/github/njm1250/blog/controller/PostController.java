package com.github.njm1250.blog.controller;

import com.github.njm1250.blog.dto.PostDto;
import com.github.njm1250.blog.entity.Post;
import com.github.njm1250.blog.entity.User;
import com.github.njm1250.blog.repository.PostRepository;
import com.github.njm1250.blog.service.Impl.UserServiceImpl;
import com.github.njm1250.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 블로그 관리자가 작성한 글 조회
    @GetMapping("/api/v1/blog/getPosts")
    public ResponseEntity<List<PostDto>> getPosts() {
        List<Object[]> results = postRepository.findPostsByAdminUsers();
        List<PostDto> postDtos = results.stream()
                .map(result -> {
                    Post post = (Post) result[0]; // SELECT p(Post)
                    String username = (String) result[1]; // SELECT u.username(User)
                    return PostDto.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .username(username)
                            .writtenDate(post.getWrittenDate())
                            .lastModified(post.getLastModified())
                            .build();
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDtos);
    }

    // 글 등록
    @PostMapping("/api/v1/blog/post")
    public ResponseEntity<String> createPost(@Valid @RequestBody PostDto postDto, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
            }
            Post post = Post.builder()
                    .user(user)
                    .title(postDto.getTitle())
                    .content(postDto.getContent())
                    .build();
            postRepository.save(post);
            return ResponseEntity.ok("Post created successfully");
        } catch (Exception e) {
            logger.error("Error creating post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the post");
        }
    }
}
