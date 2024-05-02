package org.example.servlet.mapper;

import org.example.mapper.UserDtoMapper;
import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.example.dto.UserIncomingDto;
import org.example.dto.UserOutGoingDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperTest {
    private final UserDtoMapper mapper = Mappers.getMapper(UserDtoMapper.class);

    @Test
    void mapToUser() {
        UserIncomingDto incomingDto = new UserIncomingDto("testUser");
        User user = mapper.mapToUser(incomingDto);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    void mapToOutGoing() {
        User user = new User(1L, "testUser", Collections.emptyList(), Collections.emptyList());
        UserOutGoingDto outGoingDto = mapper.mapToOutGoing(user);
        assertEquals("testUser", outGoingDto.getUsername());
    }

    @Test
    void mapToUotGoings() {
        User user1 = new User(1L, "user1", Collections.emptyList(), Collections.emptyList());
        User user2 = new User(2L, "user2", Collections.emptyList(), Collections.emptyList());
        List<User> users = Arrays.asList(user1, user2);

        List<UserOutGoingDto> outGoingDtos = mapper.mapToUotGoings(users);

        assertEquals(2, outGoingDtos.size());
        assertEquals("user1", outGoingDtos.get(0).getUsername());
        assertEquals("user2", outGoingDtos.get(1).getUsername());
    }

    @Test
    void mapById() {
        Post post = new Post();
        post.setId(123L);
        Long postId = mapper.mapById(post);
        assertEquals(123L, postId);
    }

    @Test
    void testMapById() {
        Album album = new Album();
        album.setId(456L);
        Long albumId = mapper.mapById(album);
        assertEquals(456L, albumId);
    }
}