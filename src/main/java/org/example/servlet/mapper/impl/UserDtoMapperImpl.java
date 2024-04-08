package org.example.servlet.mapper.impl;

import org.example.db.ConnectionManager;
import org.example.db.HikariConnectionManager;
import org.example.model.User;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;

import java.util.List;
//
//public class UserDtoMapperImpl implements UserDtoMapper {
//    private static UserDtoMapper instance;
//    private static ConnectionManager connectionManager;
//
//    private UserDtoMapperImpl(ConnectionManager connectionManager) {
//        UserDtoMapperImpl.connectionManager = connectionManager;
//    }
//    public static UserDtoMapper getInstance() {
//        if (instance == null) {
//            instance = new UserDtoMapperImpl(HikariConnectionManager.getInstance());
//        }
//        return instance;
//    }
//
//    public static UserDtoMapper getInstance(ConnectionManager connectionManager) {
//        if (instance == null) {
//            instance = new UserDtoMapperImpl(connectionManager);
//        }
//        return instance;
//    }
//
//    @Override
//    public User map(UserIncomingDto userIncomingDto) {
//        if (userIncomingDto == null) {
//            return null;
//        }
//        User user = new User();
//        user.setId(userIncomingDto.getId());
//        user.setUsername(userIncomingDto.getUsername());
//        return user;
//    }
//
//    @Override
//    public UserOutGoingDto map(User user) {
//        if (user == null) {
//            return null;
//        }
//        return new UserOutGoingDto(user.getUsername(), user.getPosts(), user.getAlbums());
//    }
//
//    @Override
//    public List<UserOutGoingDto> map(List<User> user) {
//        return user.stream().map(this::map).toList();
//    }
//}
