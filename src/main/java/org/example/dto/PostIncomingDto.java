package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostIncomingDto {
    private long id;
    private String content;
    private long author;

    public PostIncomingDto() {
    }

    public PostIncomingDto(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public PostIncomingDto(@JsonProperty("content") String content, @JsonProperty("author") long author) {
        this.content = content;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public long getAuthor() {
        return author;
    }
}
