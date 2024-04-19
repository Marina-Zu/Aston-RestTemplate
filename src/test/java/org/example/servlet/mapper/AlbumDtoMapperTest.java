package org.example.servlet.mapper;

import org.example.model.Album;
import org.example.model.Post;
import org.example.servlet.dto.AlbumIncomingDto;
import org.example.servlet.dto.AlbumOutGoingDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class AlbumDtoMapperTest {

    private final AlbumDtoMapper mapper = Mappers.getMapper(AlbumDtoMapper.class);

    @Test
    void testMap() {
        AlbumIncomingDto incomingDto = new AlbumIncomingDto("Test Album", "Test Description", 1L);
        Album album = mapper.map(incomingDto);
        assertEquals("Test Album", album.getTitle());
        assertEquals("Test Description", album.getDescription());
        assertEquals(1L, album.getAuthorId());
    }

    @Test
    void testMapToOutGoing() {
        List<Long> postIds = Arrays.asList(1L, 2L);
        Album album = new Album(1L, "Test Album", "Test Description", 1L, Collections.emptyList());
        AlbumOutGoingDto outGoingDto = mapper.map(album);
        assertEquals("Test Album", outGoingDto.getTitle());
        assertEquals("Test Description", outGoingDto.getDescription());
        assertEquals(1L, outGoingDto.getAuthorId());
    }

    @Test
    void testMapToList() {
        Album album1 = new Album(1L, "Test Album 1", "Test Description 1", 1L, Collections.emptyList());
        Album album2 = new Album(2L, "Test Album 2", "Test Description 2", 2L, Collections.emptyList());
        List<Album> albums = Arrays.asList(album1, album2);

        List<AlbumOutGoingDto> outGoingDtos = mapper.map(albums);

        assertEquals(2, outGoingDtos.size());
        assertEquals("Test Album 1", outGoingDtos.get(0).getTitle());
        assertEquals("Test Album 2", outGoingDtos.get(1).getTitle());
    }

    @Test
    void testMapToId_Post() {
        Post post = new Post();
        post.setId(123L);
        Long postId = mapper.mapToId(post);
        assertEquals(123L, postId);
    }
}