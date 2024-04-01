package org.example.servlet.mapper.impl;

import org.example.model.Album;
import org.example.model.User;
import org.example.repository.AlbumRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.AlbumRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.servlet.dto.AlbumIncomingDto;
import org.example.servlet.dto.AlbumOutGoingDto;
import org.example.servlet.mapper.AlbumDtoMapper;

import java.util.List;

public class AlbumDtoMapperImpl implements AlbumDtoMapper {
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private AlbumRepository albumRepository = AlbumRepositoryImpl.getInstance();

    private static AlbumDtoMapper instance;

    public static synchronized AlbumDtoMapper getInstance() {
        if (instance == null) {
            instance = new AlbumDtoMapperImpl();
        }
        return instance;
    }

    @Override
    public Album map(AlbumIncomingDto albumIncomingDto) {
        if (albumIncomingDto == null) {
            return null;
        }
        Album album;
        if (albumRepository.findById(albumIncomingDto.getId()) == null) {
            album = new Album();
        } else {
            album = albumRepository.findById(albumIncomingDto.getId());
        }
        album.setId(albumIncomingDto.getId());
        if (albumIncomingDto.getTitle() != null)
            album.setTitle(albumIncomingDto.getTitle());
        if (albumIncomingDto.getDescription() != null)
            album.setDescription(albumIncomingDto.getDescription());
        User user = userRepository.findById(albumIncomingDto.getAuthorId());
        album.setAuthorId(user.getId());
        return album;
    }

    @Override
    public AlbumOutGoingDto map(Album album) {
        if (album == null) {
            return null;
        }
        return new AlbumOutGoingDto(album.getTitle(), album.getDescription(), album.getAuthorId(), album.getPosts());
    }

    @Override
    public List<AlbumOutGoingDto> map(List<Album> albums) {
        return albums.stream().map(this::map).toList();
    }
}
