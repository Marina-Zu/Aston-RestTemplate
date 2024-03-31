package org.example.servlet.dto;

import org.example.model.Album;
import org.example.model.User;

import java.util.List;

public class PostOutGoingDto {
    private String content;
    private User author;
    private List<Album> albums;

    public PostOutGoingDto(String content, User author, List<Album> albums) {
        this.content = content;
        this.author = author;
        this.albums = albums;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
