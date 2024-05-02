package org.example.repository;

import org.example.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    //  List<Long> findAllPostIdsByAlbumId(Long albumId);

    //  void addPost(long albumId, long postId);
}
