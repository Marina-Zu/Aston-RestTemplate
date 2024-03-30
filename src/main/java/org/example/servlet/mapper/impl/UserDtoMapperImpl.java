package org.example.servlet.mapper.impl;

import org.example.model.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;

import java.util.List;

public class UserDtoMapperImpl implements UserDtoMapper {
    private UserService userService = UserServiceImpl.getInstance();

    private static UserDtoMapper instance;

    private UserDtoMapperImpl() {
    }

    public static synchronized UserDtoMapper getInstance() {
        if (instance == null) {
            instance = new UserDtoMapperImpl();
        }
        return instance;
    }

    @Override
    public User map(UserIncomingDto userIncomingDto) {
        if (userIncomingDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userIncomingDto.getId());
        user.setUsername(userIncomingDto.getUsername());
        return user;
    }

    @Override
    public UserOutGoingDto map(User user) {
        if (user == null) {
            return null;
        }
        return new UserOutGoingDto(user.getUsername(), user.getPosts(), user.getAlbums());
    }

    @Override
    public List<UserOutGoingDto> map(List<User> user) {
        return user.stream().map(this::map).toList();
    }
}
