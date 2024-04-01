package org.example.service.impl;

import org.example.model.Album;
import org.example.repository.AlbumRepository;
import org.example.repository.impl.AlbumRepositoryImpl;
import org.example.service.AlbumService;
import org.example.service.PostService;
import org.example.servlet.dto.AlbumIncomingDto;
import org.example.servlet.dto.AlbumOutGoingDto;
import org.example.servlet.mapper.AlbumDtoMapper;
import org.example.servlet.mapper.impl.AlbumDtoMapperImpl;

import java.util.List;

public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository = AlbumRepositoryImpl.getInstance();
    private static AlbumDtoMapper albumDtoMapper = AlbumDtoMapperImpl.getInstance();
    private static AlbumService instance;
    public static synchronized AlbumService getInstance() {
        if (instance == null) {
            instance = new AlbumServiceImpl();
        }
        return instance;
    }
    @Override
    public AlbumOutGoingDto save(AlbumIncomingDto albumIncomingDto) {
       Album album = albumRepository.save(albumDtoMapper.map(albumIncomingDto));
       return albumDtoMapper.map(album);
    }

    @Override
    public void update(AlbumIncomingDto albumIncomingDto) {
        Album album = albumDtoMapper.map(albumIncomingDto);
        albumRepository.update(album);
    }

    @Override
    public boolean deleteById(long id) {
        return albumRepository.deleteById(id);
    }

    @Override
    public AlbumOutGoingDto findById(long id) {
        Album album = albumRepository.findById(id);
        return albumDtoMapper.map(album);
    }

    @Override
    public List<AlbumOutGoingDto> findAll() {
        List<Album> albums = albumRepository.findAll();
        return albumDtoMapper.map(albums);
    }

    @Override
    public void addPost(long albumId, long postId) {
        albumRepository.addPost(albumId, postId);
    }
}
