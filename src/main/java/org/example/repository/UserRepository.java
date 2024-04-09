package org.example.repository;

import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;

import java.util.List;

public interface UserRepository extends Repository<User, Long>{
    List<Post> findPostsByUserId(Long id);

    List<Album> findAllByAuthorId(Long id);
}
