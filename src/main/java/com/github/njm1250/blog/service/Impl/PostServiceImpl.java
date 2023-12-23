package com.github.njm1250.blog.service.Impl;

import com.github.njm1250.blog.dto.CommentDto;
import com.github.njm1250.blog.dto.PostDto;
import com.github.njm1250.blog.dto.UserDto;
import com.github.njm1250.blog.entity.Comment;
import com.github.njm1250.blog.entity.Post;
import com.github.njm1250.blog.entity.User;
import com.github.njm1250.blog.repository.CommentRepository;
import com.github.njm1250.blog.repository.PostRepository;
import com.github.njm1250.blog.repository.UserRepository;
import com.github.njm1250.blog.service.PostService;
import com.github.njm1250.blog.utils.PostProjection;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public Comment createComment(CommentDto commentDto, UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PostProjection postProjection = postRepository.findPostById(commentDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Post post = postProjection.getPost();
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .commentText(commentDto.getCommentText())
                .build();
        // 부모댓글이 있으면 해당 댓글을 조회 후 set
        if (commentDto.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(commentDto.getParentCommentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }
        Comment savedComment = commentRepository.save(comment);
        // 댓글 수 증가
        post.incrementCommentCount();
        postRepository.save(post);
        return savedComment;
    }

    // 관리자가 작성한 글 조회
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
    public Post createPost(PostDto postDto, UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = Post.builder()
                .user(user)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        return postRepository.save(post);
    }

    @Override
    public PostDto getPostDtoById(Long postId) {
        // Object[] 방식으론 Post 객체를 이유는 모르겠지만 못 읽어와서 PostProjection 을 사용해서 받아옴
        return postRepository.findPostById(postId)
                .map(projection -> {
                    Post post = projection.getPost();
                    return PostDto.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .writtenDate(post.getWrittenDate())
                            .username(projection.getUsername())
                            .build();
                })
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
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
