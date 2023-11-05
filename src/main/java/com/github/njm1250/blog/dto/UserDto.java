package com.github.njm1250.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserDto {

    private Long userId;

    @NotBlank(message = "유저네임이 빈 값입니다.")
    @Size(min = 2, max = 12, message = "유저네임은 2글자에서 12글자 사이여야 합니다")
    private String username;

    @NotBlank(message = "비밀번호가 빈 값입니다.")
    @Size(min = 4, message = "비밀번호는 최소 4자리 이상이여야 합니다.")
    private String rawPassword;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean isAdmin;

    @Size(max = 200, message = "")
    private String profileImage;

    private LocalDateTime lastLogin;

}