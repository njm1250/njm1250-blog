package com.github.njm1250.blog.repository;

import com.github.njm1250.blog.dto.CommentDto;
import com.github.njm1250.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
    List<CommentDto> findCommentsByPostId(Long postId);
}
