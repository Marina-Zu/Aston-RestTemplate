package org.example.service;

import org.example.exception.NotFoundException;
import org.example.dto.AlbumIncomingDto;
import org.example.dto.AlbumOutGoingDto;

import java.util.List;

public interface AlbumService {
    AlbumOutGoingDto save(AlbumIncomingDto albumIncomingDto);

    void update(AlbumIncomingDto albumIncomingDto) throws NotFoundException;

    boolean deleteById(long id);

    AlbumOutGoingDto findById(long id) ;

    List<AlbumOutGoingDto> findAll();

    void addPostInAlbum(long albumId, long postId) throws NotFoundException;
}
