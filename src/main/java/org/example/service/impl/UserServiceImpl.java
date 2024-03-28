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
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final UserDtoMapper userDtoMapper = UserDtoMapperImpl.getInstance();
    private static UserService instance;

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }
    @Override
    public UserOutGoingDto save(UserIncomingDto userIncomingDto) {
        User user = userDtoMapper.map(userIncomingDto);
        userRepository.save(user);
        return userDtoMapper.map(user);
    }

    @Override
    public void update(UserIncomingDto userIncomingDto) {

    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public Optional<UserOutGoingDto> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<UserOutGoingDto> findAll() {
        return null;
    }

    @Override
    public boolean existById(long id) {
        return false;
    }
}
