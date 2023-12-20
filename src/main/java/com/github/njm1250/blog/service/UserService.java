package com.github.njm1250.blog.service;

import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.entity.User;

public interface UserService {

    /**
     * 회원가입
     *
     * @param userDto UserDto
     * @return void
     */
    void signupUser(UserDto userDto);
    /**
     * 로그인
     *
     * @param userDto UserDto
     * @return UserDto
     */
    UserDto loginUser(UserDto userDto);
}
