package org.example.servlet.mapper.impl;

import org.example.model.Post;
import org.example.model.User;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.servlet.dto.PostIncomingDto;
import org.example.servlet.dto.PostOutGoingDto;
import org.example.servlet.mapper.PostDtoMapper;

import java.util.List;

public class PostDtoMapperImpl implements PostDtoMapper {
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private PostRepository postRepository = PostRepositoryImpl.getInstance();

    private static PostDtoMapper instance;

    public static synchronized PostDtoMapper getInstance() {
        if (instance == null) {
            instance = new PostDtoMapperImpl();
        }
        return instance;
    }

    @Override
    public Post map(PostIncomingDto postIncomingDto) {
        if (postIncomingDto == null) {
            return null;
        }
        Post post = new Post();
        post.setId(postIncomingDto.getId());
        User user = userRepository.findById(postIncomingDto.getAuthor());
        post.setAuthor(user);
        post.setContent(postIncomingDto.getContent());
        return post;
    }

    @Override
    public PostOutGoingDto map(Post post) {
        if (post == null) {
            return null;
        }
        return new PostOutGoingDto(post.getContent(), post.getAuthor(), post.getAlbums());
    }

    @Override
    public List<PostOutGoingDto> map(List<Post> posts) {
        return posts.stream().map(this::map).toList();
    }
}
