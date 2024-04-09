package org.example.servlet.dto;

import java.util.List;

public class AlbumOutGoingDto {
    private String title;
    private String description;
    private long authorId;
    private List<Long> postIds;

    public AlbumOutGoingDto(String title, String description, long authorId, List<Long> posts) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.postIds = posts;
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

    public List<Long> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<Long> postIds) {
        this.postIds = postIds;
    }
}
