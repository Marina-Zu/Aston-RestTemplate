package org.example.repository;

import java.util.List;

public interface PostAlbumRepository {
    List<Long> findAllPostIdsByAlbumId(Long albumId);
}
