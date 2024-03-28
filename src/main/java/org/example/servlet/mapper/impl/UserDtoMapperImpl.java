package org.example.servlet.mapper.impl;

import org.example.model.User;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;

public class UserDtoMapperImpl implements UserDtoMapper {

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
}
