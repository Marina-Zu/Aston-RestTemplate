package org.example.service.impl;

import org.example.db.HikariConnectionManager;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.impl.UserDtoMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        userRepository.save(user);
        return userDtoMapper.map(user);
    }

    @Override
    public void update(UserIncomingDto userIncomingDto) {
//        if (userIncomingDto == null) {
//            return Optional.empty();
//        }
//        Optional<UserOutGoingDto> userOutGoingDto = userService.findById(userIncomingDto.getId());
//        if (userOutGoingDto.isPresent()) {
//            return userOutGoingDto;
//        }
//        User user = new User();
//        user.setId(userIncomingDto.getId());
//        user.setUsername(userIncomingDto.getUsername());
//        return Optional.of(userDtoMapper.map(user));
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public UserOutGoingDto findById(long id) {
        User user = userRepository.findById(id);
        return userDtoMapper.map(user);
    }


    @Override
    public List<UserOutGoingDto> findAll() {
        String SQL_QUERY = "select * from users";
        List<User> employees = null;
        try (Connection con = HikariConnectionManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_QUERY);
             ResultSet rs = pst.executeQuery();) {
            employees = new ArrayList<>();
            User user;
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));

                employees.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDtoMapper.map(employees);

    }

    @Override
    public boolean existById(long id) {
        return false;
    }
}
