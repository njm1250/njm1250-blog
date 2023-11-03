package com.github.njm1250.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostDto {

    private Long postId;
    private String title;
    private String content;
    private LocalDateTime writtenDate;
    private LocalDateTime lastModified;
}
