package org.example.servlet.dto;

import org.example.model.Album;
import org.example.model.Post;

import java.util.List;

public class UserOutGoingDto {
    private String username;
    private List<Post> posts;
    private List<Album> albums;

    public UserOutGoingDto(String username, List<Post> posts, List<Album> albums) {
        this.username = username;
        this.posts = posts;
        this.albums = albums;
    }

    public String getUsername() {
        return username;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Album> getAlbums() {
        return albums;
    }
}
