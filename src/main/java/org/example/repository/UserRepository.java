package org.example.repository;

import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT p FROM Post p WHERE p.author.id = :userId")
    List<Post> findByAuthorId(Long id);

    @Query("SELECT a FROM Album a WHERE a.authorId = :authorId")
    List<Album> findAllAlbumsByAuthorId(Long id);
}