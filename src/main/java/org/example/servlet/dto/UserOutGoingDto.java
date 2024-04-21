package org.example.servlet.dto;

import java.util.List;

public class UserOutGoingDto {
    private String username;
    private List<Long> posts;
    private List<Long> albums;

    public UserOutGoingDto(String username, List<Long> posts, List<Long> albums) {
        this.username = username;
        this.posts = posts;
        this.albums = albums;
    }

    public UserOutGoingDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Long> getPosts() {
        return posts;
    }

    public void setPosts(List<Long> posts) {
        this.posts = posts;
    }

    public List<Long> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Long> albums) {
        this.albums = albums;
    }
}
