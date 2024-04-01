package org.example.servlet.dto;

import org.example.model.Post;

import java.util.List;

public class AlbumOutGoingDto {
    private String title;
    private String description;
    private long authorId;
    private List<Post> posts;

    public AlbumOutGoingDto(String title, String description, long authorId, List<Post> posts) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.posts = posts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
