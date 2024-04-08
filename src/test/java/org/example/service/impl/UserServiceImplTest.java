package org.example.service.impl;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.impl.UserDtoMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void save() {
        UserIncomingDto incomingDto = new UserIncomingDto();
        incomingDto.setUsername("user");
        User user = new User();
        user.setUsername("user");
        UserOutGoingDto expectedDto = new UserOutGoingDto();
        expectedDto.setUsername("user");

        when(userRepository.save(user)).thenReturn(user);

        UserOutGoingDto result = userService.save(incomingDto);

        assertEquals(expectedDto.getUsername(), result.getUsername());
    }

    @Test
    void update() {
        // Arrange
        UserIncomingDto incomingDto = new UserIncomingDto();
        incomingDto.setUsername("user");
        User user = new User();
        user.setUsername("user");

        when(userDtoMapper.map(incomingDto)).thenReturn(user);

        // Act
        userService.update(incomingDto);

        // Assert
        verify(userDtoMapper).map(incomingDto);
        verify(userRepository).update(user);
    }

    @Test
    void deleteById() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void addPost() {
    }
}