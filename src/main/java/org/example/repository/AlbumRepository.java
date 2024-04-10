package org.example.repository;

import org.example.model.Album;

import java.util.List;

public interface AlbumRepository extends Repository<Album, Long> {

    List<Long> findAllPostIdsByAlbumId(Long albumId);

    void addPost(long albumId, long postId);
}
