package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.Album;
import org.example.model.Post;
import org.example.repository.AlbumRepository;
import org.example.repository.PostRepository;
import org.example.service.AlbumService;
import org.example.dto.AlbumIncomingDto;
import org.example.dto.AlbumOutGoingDto;
import org.example.mapper.AlbumDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDtoMapper albumDtoMapper;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private PostRepository postRepository;

//    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumDtoMapper albumDtoMapper, PostRepository postRepository) {
//        this.albumRepository = albumRepository;
//        this.albumDtoMapper = albumDtoMapper;
//        this.postRepository = postRepository;
//    }

    @Override
    @Transactional
    public AlbumOutGoingDto save(AlbumIncomingDto albumIncomingDto) {
        Album album = albumRepository.save(albumDtoMapper.map(albumIncomingDto));
        return albumDtoMapper.map(album);
    }

    @Override
    @Transactional
    public void update(AlbumIncomingDto albumIncomingDto) throws NotFoundException {
        Album album = searchAlbumById(albumIncomingDto.getId());
        if (albumIncomingDto.getTitle() != null) {
            album.setTitle(albumIncomingDto.getTitle());
        }
        if (albumIncomingDto.getDescription() != null) {
            album.setDescription(albumIncomingDto.getDescription());
        }
        albumRepository.save(album);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        albumRepository.deleteById(id);
        return true;
    }

    @Override
    public AlbumOutGoingDto findById(long id) {
        Album album = null;
        try {
            album = albumRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Album not found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return albumDtoMapper.map(album);
    }

    @Override
    public List<AlbumOutGoingDto> findAll() {
        List<Album> albums = albumRepository.findAll();
        return albumDtoMapper.map(albums);
    }

    @Override
    @Transactional
    public void addPostInAlbum(long albumId, long postId) throws NotFoundException {
//        checkExistAlbum(albumId);
//        checkExistPost(postId);

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new NotFoundException("Album not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        album.getPosts().add(post);
        albumRepository.save(album);
    }

    private Album searchAlbumById(long id) throws NotFoundException {
        return albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album not found"));
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
