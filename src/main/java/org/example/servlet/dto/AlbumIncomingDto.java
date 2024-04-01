package org.example.servlet.dto;

public class AlbumIncomingDto {
    private long id;
    private String title;
    private String description;
    private long authorId;

    public AlbumIncomingDto() {
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
}
