package org.example.service.impl;

import org.example.model.Album;
import org.example.model.Post;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
    void testSaveSuccessful() {
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
    void testUpdateSuccessful() {
        UserIncomingDto incomingDto = new UserIncomingDto();
        incomingDto.setUsername("user");
        User user = new User();
        user.setUsername("user");

        when(userDtoMapper.mapToUser(any(UserIncomingDto.class))).thenReturn(user);

        userService.update(incomingDto);

        verify(userDtoMapper).mapToUser(any(UserIncomingDto.class));
        verify(userRepository).update(user);
    }

    @Test
    void testDeleteByIdSuccessful() {
        long userId = 1L;

        userService.deleteById(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void testFindByIdSuccessful() {
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("user");
        UserOutGoingDto expectedDto = new UserOutGoingDto();
        expectedDto.setUsername("user");

        List<Post> posts = Collections.singletonList(new Post());
        List<Album> albums = Collections.singletonList(new Album());
        user.setPosts(posts);
        user.setAlbums(albums);

        when(userRepository.findById(userId)).thenReturn(user);
        when(userRepository.findPostsByUserId(userId)).thenReturn(posts);
        when(userRepository.findAllByAuthorId(userId)).thenReturn(albums);
        when(userDtoMapper.mapToOutGoing(user)).thenReturn(expectedDto);

        UserOutGoingDto result = userService.findById(userId);

        assertEquals(expectedDto.getUsername(), result.getUsername());

        verify(userRepository).findById(userId);
        verify(userRepository).findPostsByUserId(userId);
        verify(userRepository).findAllByAuthorId(userId);
        verify(userDtoMapper).mapToOutGoing(user);
    }

    @Test
    void teatFindAllSuccessful() {
        List<User> users = Arrays.asList(new User(), new User());
        List<UserOutGoingDto> expectedDtos = Arrays.asList(new UserOutGoingDto(), new UserOutGoingDto());

        when(userRepository.findAll()).thenReturn(users);
        when(userDtoMapper.mapToUotGoings(users)).thenReturn(expectedDtos);

        List<UserOutGoingDto> result = userService.findAll();

        assertEquals(expectedDtos.size(), result.size());
        for (int i = 0; i < expectedDtos.size(); i++) {
            assertEquals(expectedDtos.get(i), result.get(i));
        }

        verify(userRepository).findAll();
        verify(userDtoMapper).mapToUotGoings(users);
    }
}