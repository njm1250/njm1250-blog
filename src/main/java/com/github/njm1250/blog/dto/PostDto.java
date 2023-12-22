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
public class PostDto {

    private Long postId;
    @NotBlank(message = "제목이 빈 값입니다.")
    private String title;
    @NotBlank(message = "내용이 빈 값입니다.")
    private String content;
    private String username;
    private LocalDateTime writtenDate;
    private LocalDateTime lastModified;
}
