package org.example.repository;

import org.example.model.Post;

import java.util.List;

public interface Repository<T, K> {
    T save(T t);

    void update(T t);

    boolean deleteById(K id);

    T findById(K id);

    List<T> findAll();

    boolean existsById(K id);

}