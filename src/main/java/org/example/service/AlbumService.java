package org.example.service;

import org.example.exeption.NotFoundException;
import org.example.servlet.dto.AlbumIncomingDto;
import org.example.servlet.dto.AlbumOutGoingDto;

import java.util.List;

public interface AlbumService {
    AlbumOutGoingDto save(AlbumIncomingDto albumIncomingDto);

    void update(AlbumIncomingDto albumIncomingDto);

    boolean deleteById(long id);

    AlbumOutGoingDto findById(long id);

    List<AlbumOutGoingDto> findAll();

    void addPost(long albumId, long postId) throws NotFoundException;
}
