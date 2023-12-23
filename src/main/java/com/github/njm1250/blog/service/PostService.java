package com.github.njm1250.blog.service;

import com.github.njm1250.blog.dto.CommentDto;
import com.github.njm1250.blog.dto.PostDto;
import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.entity.Comment;
import com.github.njm1250.blog.entity.Post;
import com.github.njm1250.blog.entity.User;


import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostDto> getPostDtoListByAdmin();
    PostDto getPostDtoById(Long postId);
    List<CommentDto> getCommentDtoListByPostId(Long postId);
    Post createPost(PostDto postDto, UserDto userDto);
    Comment createComment(CommentDto commentDto, UserDto userDto);
}
