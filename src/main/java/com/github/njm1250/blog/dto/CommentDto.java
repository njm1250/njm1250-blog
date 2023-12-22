package com.github.njm1250.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentDto {

    private Long postId;
    private Long userId;
    @NotBlank(message = "댓글이 빈 값입니다.")
    private String commentText;
    private LocalDateTime commentDate;

}
