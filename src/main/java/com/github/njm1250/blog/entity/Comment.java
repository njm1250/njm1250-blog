package com.github.njm1250.blog.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @CreationTimestamp
    @Column(name = "comment_date", nullable = false, updatable = false)
    private LocalDateTime commentDate;

    // 대댓글 기능. 나중에 구현
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;


    @Builder
    public Comment(Post post, User user, String commentText, Comment parentComment) {
        this.post = post;
        this.user = user;
        this.commentText = commentText;
        this.parentComment = parentComment;
    }
}
