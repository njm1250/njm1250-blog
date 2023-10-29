package com.github.njm1250.blog.service.Impl;

import com.github.njm1250.blog.dto.UserDTO;
import com.github.njm1250.blog.entity.User;
import com.github.njm1250.blog.repository.UserRepository;
import com.github.njm1250.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void registerUser(UserDTO userDTO) {
        String encodedPassword = encodePassword(userDTO.getRawPassword());
        User user = User.builder()
                .username(userDTO.getUsername())
                .passwordHash(encodedPassword)
                .profileImage(userDTO.getProfileImage())
                .isAdmin(false)
                .build();
        logger.debug("registed username : {}", userDTO.getUsername());
        userRepository.save(user);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }


}
