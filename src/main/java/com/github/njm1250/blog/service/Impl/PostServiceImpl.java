package com.github.njm1250.blog.service.Impl;

import com.github.njm1250.blog.dto.PostDto;
import com.github.njm1250.blog.entity.Post;
import com.github.njm1250.blog.entity.User;
import com.github.njm1250.blog.repository.PostRepository;
import com.github.njm1250.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDto> getPostDtosByAdmin() {
        List<Object[]> results = postRepository.findPostsByAdminUsers();
        return results.stream()
                .map(result -> {
                    Post post = (Post) result[0]; // SELECT p(Post)
                    String username = (String) result[1]; // SELECT u.username(User)
                    return PostDto.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .username(username)
                            .writtenDate(post.getWrittenDate())
                            .lastModified(post.getLastModified())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Post createPost(PostDto postDto, User user) {
        Post post = Post.builder()
                .user(user)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        return postRepository.save(post);
    }

    @Override
    public PostDto getPostDtoById(Long postId) {
        return postRepository.findPostByPostId(postId)
                .map(result -> {
                    Post post = (Post) result[0];
                    String username = (String) result[1];

                    return PostDto.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .username(username)
                            .writtenDate(post.getWrittenDate())
                            .build();
                }).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
    }


    private PostDto convertToDto(Post post) {
        PostDto postDto = PostDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        return postDto;
    }
}
