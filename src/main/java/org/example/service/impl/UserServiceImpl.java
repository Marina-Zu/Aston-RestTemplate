package org.example.service.impl;

import org.example.exception.DataValidationException;
import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.example.dto.UserIncomingDto;
import org.example.dto.UserOutGoingDto;
import org.example.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDtoMapper userDtoMapper;

//    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper) {
//        this.userRepository = userRepository;
//        this.userDtoMapper = userDtoMapper;
//    }


    @Override
    @Transactional
    public UserOutGoingDto save(UserIncomingDto userIncomingDto) {
        User user = userRepository.save(userDtoMapper.mapToUser(userIncomingDto));
        return userDtoMapper.mapToOutGoing(user);
    }

    @Override
    @Transactional
    public void update(UserIncomingDto userIncomingDto) {
        //  User user = userDtoMapper.mapToUser(userIncomingDto);
        User user = searchUserById(userIncomingDto.getId());
        user.setUsername(userIncomingDto.getUsername());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        User user = searchUserById(id);
        userRepository.delete(user);
        return true;
    }

    @Override
    public UserOutGoingDto findById(long id) {
        User user = searchUserById(id);
        List<Post> posts = userRepository.findByAuthorId(id);
        List<Album> albums = userRepository.findAllAlbumsByAuthorId(id);
        user.setPosts(posts);
        user.setAlbums(albums);
        return userDtoMapper.mapToOutGoing(user);
    }

    @Override
    public List<UserOutGoingDto> findAll() {
        List<User> users = userRepository.findAll();
        return userDtoMapper.mapToUotGoings(users);
    }

    public User searchUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new DataValidationException("User with id " + id + " not found."));

    }

//    public void addPost(Post post) {
//        User author = post.getAuthor();
//        author.getPosts().add(post);
//    }
}