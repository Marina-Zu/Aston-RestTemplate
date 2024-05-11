package org.example.controller;

import org.example.dto.PostIncomingDto;
import org.example.dto.PostOutGoingDto;
import org.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public List<PostOutGoingDto> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostOutGoingDto getPostById(@PathVariable long id) {
        return postService.findById(id);
    }

    @PostMapping
    public PostOutGoingDto createPost(@RequestBody PostIncomingDto postDto) {
        return postService.save(postDto);
    }

    @PutMapping("/{id}")
    public String updatePost(@PathVariable long id, @RequestBody PostIncomingDto postDto) {
        postService.update(postDto);
        return "Post with id " + id + " updated";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable long id) {
        postService.deleteById(id);
        return "Post with id " + id + " deleted";
    }

}
