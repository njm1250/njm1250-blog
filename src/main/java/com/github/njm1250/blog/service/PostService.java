package com.github.njm1250.blog.service;

import com.github.njm1250.blog.dto.PostDto;
import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.entity.Post;
import com.github.njm1250.blog.entity.User;


import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostDto> getPostDtosByAdmin();
    PostDto getPostDtoById(Long postId);
    Post createPost(PostDto postDto, UserDto userDto);
}
