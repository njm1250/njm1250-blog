package com.github.njm1250.blog.service.Impl;

import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.entity.User;
import com.github.njm1250.blog.repository.UserRepository;
import com.github.njm1250.blog.service.UserService;
import com.github.njm1250.blog.utils.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto loginUser(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        if (!verifyPassword(userDto.getRawPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Wrong password");
        }
        updateLastLoginTime(user);
        return convertToDto(user);
    }

    @Override
    @Transactional
    public void signupUser(UserDto userDto) {
        userRepository.findByUsername(userDto.getUsername()).ifPresent(u -> {
            throw new IllegalStateException("Username already exists");
        });
        User user = User.builder()
                .username(userDto.getUsername())
                .passwordHash(encodePassword(userDto.getRawPassword()))
                .profileImage(userDto.getProfileImage())
                .isAdmin(false)
                .build();
        userRepository.save(user);
        logger.debug("Registered username: {}", userDto.getUsername());
    }

    private void updateLastLoginTime(User user) {
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .isAdmin(user.getIsAdmin())
                .build();
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private boolean verifyPassword (String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
