package org.example.dto;

public class AlbumIncomingDto {
    private long id;
    private String title;
    private String description;
    private long authorId;

    public AlbumIncomingDto() {
    }

    public AlbumIncomingDto(String title, String description, long authorId) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
}
