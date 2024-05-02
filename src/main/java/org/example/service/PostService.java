package org.example.service;

import org.example.dto.PostIncomingDto;
import org.example.dto.PostOutGoingDto;

import java.util.List;

public interface PostService {
    PostOutGoingDto save(PostIncomingDto postIncomingDto);

    void update(PostIncomingDto postIncomingDto);

    boolean deleteById(long id);

    PostOutGoingDto findById(long id);

    List<PostOutGoingDto> findAll();
}
