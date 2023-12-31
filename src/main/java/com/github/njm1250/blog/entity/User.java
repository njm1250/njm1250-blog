package com.github.njm1250.blog.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin = false;

    @Builder
    public User(String username, String passwordHash, String profileImage, Boolean isAdmin) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.profileImage = profileImage;
        this.isAdmin = isAdmin;
    }
}
