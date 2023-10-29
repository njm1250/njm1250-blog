package com.github.njm1250.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserDTO {

    private Long userId;
    private String username;
    private String rawPassword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String profileImage;
    private LocalDateTime lastLogin;

}