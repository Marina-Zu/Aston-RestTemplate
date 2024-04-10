package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.example.repository.AlbumRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.example.service.AlbumService;
import org.example.servlet.dto.AlbumIncomingDto;
import org.example.servlet.dto.AlbumOutGoingDto;
import org.example.servlet.mapper.AlbumDtoMapper;

import java.util.ArrayList;
import java.util.List;

public class AlbumServiceImpl implements AlbumService {
    private final AlbumDtoMapper albumDtoMapper;
    private final AlbumRepository albumRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumDtoMapper albumDtoMapper, PostRepository postRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.albumDtoMapper = albumDtoMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
    public void addPost(long albumId, long postId) throws NotFoundException {
        checkExistAlbum(albumId);
        checkExistPost(postId);

        Album album = albumRepository.findById(albumId);
        album.getPosts().add(postRepository.findById(postId));
        albumRepository.addPost(albumId, postId);
    }


    private void checkExistPost(long postId) throws NotFoundException {
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException("Post not found.");
        }
    }

    private void checkExistAlbum(long albumId) throws NotFoundException {
        if (!albumRepository.existsById(albumId)) {
            throw new NotFoundException("Album not found.");
        }
    }
}
