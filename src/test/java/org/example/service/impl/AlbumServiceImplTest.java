//package org.example.service.impl;
//
//import org.example.exception.NotFoundException;
//import org.example.model.Album;
//import org.example.model.Post;
//import org.example.repository.AlbumRepository;
//import org.example.repository.PostRepository;
//import org.example.repository.UserRepository;
//import org.example.servlet.dto.AlbumIncomingDto;
//import org.example.servlet.dto.AlbumOutGoingDto;
//import org.example.servlet.mapper.AlbumDtoMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AlbumServiceImplTest {
//
//    @Mock
//    private AlbumRepository albumRepository;
//
//    @Mock
//    private AlbumDtoMapper albumDtoMapper;
//
//    @Mock
//    private PostRepository postRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private AlbumServiceImpl albumService;
//
//    @Test
//    void testSaveSuccessful() {
//        AlbumIncomingDto incomingDto = new AlbumIncomingDto();
//        incomingDto.setTitle("Test Album");
//        Album album = new Album();
//        album.setTitle("Test Album");
//
//        AlbumOutGoingDto expectedDto = new AlbumOutGoingDto("Test Album", null, 0L, null);
//
//        when(albumDtoMapper.map(incomingDto)).thenReturn(album);
//        when(albumRepository.save(album)).thenReturn(album);
//        when(albumDtoMapper.map(album)).thenReturn(expectedDto);
//
//        AlbumOutGoingDto result = albumService.save(incomingDto);
//
//        assertEquals(expectedDto.getTitle(), result.getTitle());
//
//        verify(albumDtoMapper).map(incomingDto);
//        verify(albumRepository).save(album);
//        verify(albumDtoMapper).map(album);
//    }
//
//    @Test
//    void testUpdateSuccessful() {
//        AlbumIncomingDto incomingDto = new AlbumIncomingDto();
//        incomingDto.setTitle("Updated Album");
//        Album album = new Album();
//        album.setTitle("Updated Album");
//
//        when(albumDtoMapper.map(incomingDto)).thenReturn(album);
//
//        albumService.update(incomingDto);
//
//        verify(albumDtoMapper).map(incomingDto);
//        verify(albumRepository).update(album);
//    }
//
//    @Test
//    void testDeleteByIdSuccessful() {
//        long albumId = 1L;
//
//        albumService.deleteById(albumId);
//
//        verify(albumRepository).deleteById(albumId);
//    }
//
//    @Test
//    void testFindByIdSuccessful() {
//        long albumId = 1L;
//        Album album = new Album();
//        album.setId(albumId);
//        album.setTitle("Test Album");
//        AlbumOutGoingDto expectedDto = new AlbumOutGoingDto("Test Album", null, 0L, null);
//
//        when(albumRepository.findById(albumId)).thenReturn(album);
//        when(albumDtoMapper.map(album)).thenReturn(expectedDto);
//
//        AlbumOutGoingDto result = albumService.findById(albumId);
//
//        assertEquals(expectedDto.getTitle(), result.getTitle());
//
//        verify(albumRepository).findById(albumId);
//        verify(albumDtoMapper).map(album);
//    }
//
//    @Test
//    void testFindAllSuccessful(){
//        List<Album> albums = Arrays.asList(new Album(), new Album());
//        List<AlbumOutGoingDto> expectedDtos = Arrays.asList(new AlbumOutGoingDto(), new AlbumOutGoingDto());
//
//        when(albumRepository.findAll()).thenReturn(albums);
//        when(albumDtoMapper.map(albums)).thenReturn(expectedDtos);
//
//        List<AlbumOutGoingDto> result = albumService.findAll();
//
//        assertEquals(expectedDtos.size(), result.size());
//        for (int i = 0; i < expectedDtos.size(); i++) {
//            assertEquals(expectedDtos.get(i), result.get(i));
//        }
//
//        verify(albumRepository).findAll();
//        verify(albumDtoMapper).map(albums);
//    }
//
//    @Test
//    void testAddPostInAlbumSuccessful() throws NotFoundException {
//        long albumId = 10L;
//        long postId = 10L;
//        Album album = new Album();
//        Post post = new Post();
//
//        when(albumRepository.existsById(albumId)).thenReturn(true);
//        when(postRepository.existsById(postId)).thenReturn(true);
//        when(albumRepository.findById(albumId)).thenReturn(album);
//        when(postRepository.findById(postId)).thenReturn(post);
//
//        albumService.addPostInAlbum(albumId, postId);
//
//        assertTrue(album.getPosts().contains(post));
//       // verify(albumRepository).addPostInAlbum(albumId, postId);
//    }
//}