package org.example.service.impl;

import org.example.model.Post;
import org.example.model.User;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.PostService;
import org.example.service.UserService;
import org.example.servlet.dto.PostIncomingDto;
import org.example.servlet.dto.PostOutGoingDto;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.impl.PostDtoMapperImpl;

import java.util.ArrayList;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository = PostRepositoryImpl.getInstance();
    private static PostDtoMapper postDtoMapper = PostDtoMapperImpl.getInstance();
    private static PostService instance;

    public static synchronized PostService getInstance() {
        if (instance == null) {
            instance = new PostServiceImpl();
        }
        return instance;
    }

    @Override
    public PostOutGoingDto save(PostIncomingDto postIncomingDto) {
        Post post = postRepository.save(postDtoMapper.map(postIncomingDto));
        return postDtoMapper.map(post);
    }

    @Override
    public void update(PostIncomingDto postIncomingDto) {
        Post post = postDtoMapper.map(postIncomingDto);
        postRepository.update(post);
    }

    @Override
    public boolean deleteById(long id) {
        return postRepository.deleteById(id);
    }

    @Override
    public PostOutGoingDto findById(long id) {
        Post post = postRepository.findById(id);
        return postDtoMapper.map(post);
    }

    @Override
    public List<PostOutGoingDto> findAll() {
        List<Post> posts = postRepository.findAll();
        return postDtoMapper.map(posts);
    }

    public List<Post> getPosts(long userId) {
        return postRepository.findAllByAuthorId(userId);
    }

}