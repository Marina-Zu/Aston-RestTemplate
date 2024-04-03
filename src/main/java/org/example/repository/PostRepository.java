package org.example.repository;

import org.example.model.Post;

import java.util.List;

public interface PostRepository extends Repository<Post, Long> {
    List<Post> findAllByAuthorId(Long id);
}
