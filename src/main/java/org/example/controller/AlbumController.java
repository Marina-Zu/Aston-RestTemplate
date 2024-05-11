package org.example.controller;

import org.example.dto.AlbumIncomingDto;
import org.example.dto.AlbumOutGoingDto;
import org.example.exception.NotFoundException;
import org.example.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/all")
    public List<AlbumOutGoingDto> getAll() {
        return albumService.findAll();
    }

    @GetMapping("/{id}")
    public AlbumOutGoingDto getById(@PathVariable long id) {
        return albumService.findById(id);
    }

    @PostMapping
    public AlbumOutGoingDto create(@RequestBody AlbumIncomingDto albumDto) {
        return albumService.save(albumDto);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, @RequestBody AlbumIncomingDto albumDto) {
        albumService.save(albumDto);
        return "Album with id " + id + " updated";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        albumService.deleteById(id);
        return "Album with id " + id + " deleted";
    }

    @PostMapping("/{albumId}/posts/{postId}")
    public String addPostToAlbum(@PathVariable long albumId, @PathVariable long postId) throws NotFoundException {
        albumService.addPostInAlbum(albumId, postId);
        return "Post with id " + postId + " added to album with id " + albumId;
    }
}
