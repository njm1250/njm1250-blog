package com.github.njm1250.blog.utils;

import com.github.njm1250.blog.entity.Post;

public interface PostProjection {
    Post getPost();
    String getUsername();
}
