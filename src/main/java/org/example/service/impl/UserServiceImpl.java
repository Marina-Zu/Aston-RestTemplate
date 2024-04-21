package org.example.service.impl;

import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }


    @Override
    public UserOutGoingDto save(UserIncomingDto userIncomingDto) {
        User user = userRepository.save(userDtoMapper.mapToUser(userIncomingDto));
        return userDtoMapper.mapToOutGoing(user);
    }

    @Override
    public void update(UserIncomingDto userIncomingDto) {
        User user = userDtoMapper.mapToUser(userIncomingDto);
        userRepository.update(user);
    }

    @Override
    public boolean deleteById(long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public UserOutGoingDto findById(long id) {
        User user = userRepository.findById(id);
        List<Post> posts = userRepository.findPostsByUserId(id);
        List<Album> albums = userRepository.findAllByAuthorId(id);
        user.setPosts(posts);
        user.setAlbums(albums);
        return userDtoMapper.mapToOutGoing(user);
    }

    @Override
    public List<UserOutGoingDto> findAll() {
        List<User> users = userRepository.findAll();
        return userDtoMapper.mapToUotGoings(users);
    }

    public void addPost(Post post) {
        User author = post.getAuthor();
        author.getPosts().add(post);
    }
}