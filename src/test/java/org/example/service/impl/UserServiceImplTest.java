package org.example.service.impl;

import org.example.model.User;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void save() {
        UserIncomingDto incomingDto = new UserIncomingDto();
        incomingDto.setUsername("user");
        User user = new User();
        user.setUsername("user");
        UserOutGoingDto expectedDto = new UserOutGoingDto();
        expectedDto.setUsername("user");

        when(userDtoMapper.mapToUser(any(UserIncomingDto.class))).thenReturn(user);
        when(userDtoMapper.mapToOutGoing(any(User.class))).thenReturn(expectedDto);
        when(userRepository.save(any(User.class))).thenReturn(user);

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

        when(userDtoMapper.mapToUser(any(UserIncomingDto.class))).thenReturn(user);

        // Act
        userService.update(incomingDto);

        // Assert
        verify(userDtoMapper).mapToUser(any(UserIncomingDto.class));
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