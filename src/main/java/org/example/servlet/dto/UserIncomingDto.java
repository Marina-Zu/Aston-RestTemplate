package org.example.servlet.dto;

public class UserIncomingDto {
    private long id;
    private String username;

    public UserIncomingDto(){
    }

    public UserIncomingDto(long id) {
        this.id = id;
    }

    public UserIncomingDto(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
