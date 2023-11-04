package com.github.njm1250.blog.repository;

import com.github.njm1250.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p, u.username FROM Post p JOIN p.user u WHERE u.isAdmin = true")
    List<Object[]> findPostsByAdminUsers();
}
