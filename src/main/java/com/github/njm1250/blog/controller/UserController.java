package com.github.njm1250.blog.controller;

import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.entity.User;
import com.github.njm1250.blog.service.UserService;
import com.github.njm1250.blog.utils.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 정보를 세션에 저장
    @GetMapping("/api/v1/users/status")
    public ResponseEntity<Map<String, Object>> getLoginStatus(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserDto userDto = (UserDto) session.getAttribute("user");
            if (userDto != null) {
                Map<String, Object> status = new HashMap<>();
                status.put("loggedIn", true);
                status.put("username", userDto.getUsername());
                status.put("isAdmin", userDto.getIsAdmin());
                return ResponseEntity.ok(status);
            }
        }
        return ResponseEntity.ok(Collections.singletonMap("loggedIn", false));
    }

    // 회원가입
    @PostMapping("/api/v1/users/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserDto userDto) {
        userService.signupUser(userDto);
        return ResponseEntity.status(201).body("User created successfully");
    }

    // 로그아웃
    @PostMapping("/api/v1/users/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/api/v1/users/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserDto userDto, HttpSession session) {
        try {
            UserDto loggedUser = userService.loginUser(userDto);
            session.setAttribute("user", loggedUser);
            return ResponseEntity.ok("Login successful");
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


}
