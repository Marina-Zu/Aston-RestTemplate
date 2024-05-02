package org.example.servlet.mapper;

import org.example.mapper.PostDtoMapper;
import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.example.dto.PostIncomingDto;
import org.example.dto.PostOutGoingDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostDtoMapperTest {

    private final PostDtoMapper mapper = Mappers.getMapper(PostDtoMapper.class);

    @Test
    void testMap() {
        PostIncomingDto incomingDto = new PostIncomingDto("test content", 1L);
        User author = new User(1L, "testUser", Collections.emptyList(), Collections.emptyList());
        Post post = mapper.map(incomingDto);
        assertEquals("test content", post.getContent());
        assertEquals(author.getId(), post.getAuthor().getId());
    }

    @Test
    void testMapToOutGoing() {
        User author = new User(1L, "testUser");
        Post post = new Post(1L, "test content", author);
        PostOutGoingDto outGoingDto = mapper.map(post);
        assertEquals("test content", outGoingDto.getContent());
        assertEquals(author.getId(), outGoingDto.getAuthorId());
    }

    @Test
    void testMapToList() {
        User author = new User(1L, "testUser");
        Post post1 = new Post(1L, "test content 1", author);
        Post post2 = new Post(2L, "test content 2", author);
        List<Post> posts = Arrays.asList(post1, post2);

        List<PostOutGoingDto> outGoingDtos = mapper.map(posts);

        assertEquals(2, outGoingDtos.size());
        assertEquals("test content 1", outGoingDtos.get(0).getContent());
        assertEquals("test content 2", outGoingDtos.get(1).getContent());
    }

    @Test
    void testMapToUser() {
        User user = mapper.mapToUser(123L);
        assertEquals(123L, user.getId());
    }

    @Test
    void testMapToId_Album() {
        Album album = new Album();
        album.setId(456L);
        Long albumId = mapper.mapToId(album);
        assertEquals(456L, albumId);
    }
}