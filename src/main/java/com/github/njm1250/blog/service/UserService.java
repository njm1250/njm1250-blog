package com.github.njm1250.blog.service;

import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.entity.User;

public interface UserService {
    void signupUser(UserDto userDto);
    UserDto loginUser(UserDto userDto);
}
