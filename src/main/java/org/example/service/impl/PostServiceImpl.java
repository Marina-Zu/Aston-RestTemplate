package org.example.service.impl;

import org.example.exception.DataValidationException;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.example.dto.PostIncomingDto;
import org.example.dto.PostOutGoingDto;
import org.example.mapper.PostDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostDtoMapper postDtoMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostDtoMapper postDtoMapper) {
        this.postRepository = postRepository;
        this.postDtoMapper = postDtoMapper;
    }

    @Override
    @Transactional
    public PostOutGoingDto save(PostIncomingDto postIncomingDto) {
        Post post1 = postDtoMapper.map(postIncomingDto);
        Post post = postRepository.save(post1);
        return postDtoMapper.map(post);
    }

    @Override
    @Transactional
    public void update(PostIncomingDto postIncomingDto) {
        Post post = searchPostById(postIncomingDto.getId());
        if (postIncomingDto.getContent() != null) {
            post.setContent(postIncomingDto.getContent());
        }
        postRepository.save(post);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        postRepository.deleteById(id);
        return true;
    }

    @Override
    public PostOutGoingDto findById(long id) {
        Post post = null;
        try {
            post = postRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Post not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        PostOutGoingDto postOutGoingDto = postDtoMapper.map(post);
        postOutGoingDto.setAlbumIds(postRepository.getAlbumIds(post.getId()));
        return postOutGoingDto;
    }

    @Override
    public List<PostOutGoingDto> findAll() {
        List<Post> posts = postRepository.findAll();
        return postDtoMapper.map(posts);
    }

    private Post searchPostById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new DataValidationException("User with id " + id + " not found."));
    }
}