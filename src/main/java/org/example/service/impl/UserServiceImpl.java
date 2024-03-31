package org.example.service.impl;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.impl.UserDtoMapperImpl;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static UserDtoMapper userDtoMapper = UserDtoMapperImpl.getInstance();
    private static UserService instance;

    private UserServiceImpl(UserDtoMapper userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl(userDtoMapper);
        }
        return instance;
    }

    @Override
    public UserOutGoingDto save(UserIncomingDto userIncomingDto) {
        User user = userRepository.save(userDtoMapper.map(userIncomingDto));
        return userDtoMapper.map(user);
    }

    @Override
    public void update(UserIncomingDto userIncomingDto) {
        User user = userDtoMapper.map(userIncomingDto);
        userRepository.update(user);
    }

    @Override
    public boolean deleteById(long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public UserOutGoingDto findById(long id) {
        User user = userRepository.findById(id);
        return userDtoMapper.map(user);
    }

    @Override
    public List<UserOutGoingDto> findAll() {
        List<User> users = userRepository.findAll();
        return userDtoMapper.map(users);

    }
}
