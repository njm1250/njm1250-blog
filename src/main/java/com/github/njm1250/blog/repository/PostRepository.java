package com.github.njm1250.blog.repository;

import com.github.njm1250.blog.dto.PostDto;
import com.github.njm1250.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p, u.username FROM Post p JOIN p.user u WHERE u.isAdmin = true")
    List<Object[]> findPostsByAdminUsers();
    @Query("SELECT p, u.username FROM Post p JOIN p.user u WHERE u.isAdmin = true AND p.postId = :postId")
    Optional<Object[]> findPostByPostId(@Param("postId") Long postId);


}
