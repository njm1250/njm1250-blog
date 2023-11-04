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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public User loginUser(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (!userOptional.isPresent()) {
            throw new InvalidCredentialsException("사용자를 찾을 수 없습니다.");
        }
        User user = userOptional.get();
        if (!verifyPassword (userDto.getRawPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("잘못된 비밀번호입니다.");
        }
        return user;
    }

    @Override
    @Transactional
    public void signupUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 사용자명입니다.");
        }
        String encodedPassword = encodePassword(userDto.getRawPassword());
        User user = User.builder()
                .username(userDto.getUsername())
                .passwordHash(encodedPassword)
                .profileImage(userDto.getProfileImage())
                .isAdmin(false)
                .build();
        logger.debug("registed username : {}", userDto.getUsername());
        userRepository.save(user);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword (String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
