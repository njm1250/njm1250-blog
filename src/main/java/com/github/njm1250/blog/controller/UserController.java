package com.github.njm1250.blog.controller;

import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.entity.User;
import com.github.njm1250.blog.service.Impl.UserServiceImpl;
import com.github.njm1250.blog.service.UserService;
import com.github.njm1250.blog.utils.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 정보를 세션에 저장
    @GetMapping("/api/v1/users/status")
    public ResponseEntity<Map<String, Object>> getLoginStatus(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserDto userDto = (UserDto) session.getAttribute("user");
            if (userDto != null) {

                Cookie sessionCookie = WebUtils.getCookie(request, "JSESSIONID");
                if (sessionCookie != null && request.isSecure()) {
                    sessionCookie.setHttpOnly(true);
                    sessionCookie.setSecure(true);
                    response.addCookie(sessionCookie);
                }

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
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
            logger.debug("User logged out successfully.");
        }
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/api/v1/users/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserDto userDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserDto loggedUser = userService.loginUser(userDto);
            HttpSession session = request.getSession(true);
            session.setAttribute("user", loggedUser);

            // 로그인 성공 후, session id 재생성,
            request.changeSessionId();
            session.setMaxInactiveInterval(30 * 60); // 30분으로 session timeout 설정

            Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
            if (request.isSecure()) {
                sessionCookie.setHttpOnly(true); // HTTPS
                sessionCookie.setSecure(true); // JS 차단
                response.addCookie(sessionCookie);
            }
            logger.debug("User {} logged in successfully.", userDto.getUsername());
            return ResponseEntity.ok("Login successful");
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}