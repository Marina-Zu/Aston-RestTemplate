package org.example.repository;

import org.example.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT a.id FROM Post p JOIN p.albums a WHERE p.id = :postId")
    List<Long> getAlbumIds(long postId);
}
