package org.example.service.impl;

import org.example.model.Post;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.example.servlet.dto.PostIncomingDto;
import org.example.servlet.dto.PostOutGoingDto;
import org.example.servlet.mapper.PostDtoMapper;

import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostDtoMapper postDtoMapper;

    public PostServiceImpl(PostRepository postRepository, PostDtoMapper postDtoMapper) {
        this.postRepository = postRepository;
        this.postDtoMapper = postDtoMapper;
    }

    @Override
    public PostOutGoingDto save(PostIncomingDto postIncomingDto) {
        Post post1 = postDtoMapper.map(postIncomingDto);

        Post post = postRepository.save(post1);
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
        PostOutGoingDto postOutGoingDto = postDtoMapper.map(post);
        postOutGoingDto.setAlbumIds(postRepository.getAlbumIds(post.getId()));
        return postOutGoingDto;
    }

    @Override
    public List<PostOutGoingDto> findAll() {
        List<Post> posts = postRepository.findAll();
        return postDtoMapper.map(posts);
    }
}