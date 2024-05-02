package org.example.dto;

import java.util.List;

public class PostOutGoingDto {
    private String content;
    private Long authorId;
    private List<Long> albumIds;

    public PostOutGoingDto() {
    }

    public PostOutGoingDto(String content, Long authorId, List<Long> albums) {
        this.content = content;
        this.authorId = authorId;
        this.albumIds = albums;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public List<Long> getAlbumIds() {
        return albumIds;
    }

    public void setAlbumIds(List<Long> albumIds) {
        this.albumIds = albumIds;
    }
}
