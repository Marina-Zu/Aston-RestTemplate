package org.example.servlet.dto;

public class UserIncomingDto {
    private long id;
    private String username;

    public UserIncomingDto() {
    }

    public UserIncomingDto(String username) {
        this.username = username;
    }

    public UserIncomingDto(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
