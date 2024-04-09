package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts;

    @ManyToMany(mappedBy = "albums")
    private List<Album> albums;

    public User() {
    }

    public User(long id, String username, List<Post> posts, List<Album> albums) {
        this.id = id;
        this.username = username;
        this.posts = posts;
        this.albums = albums;
    }

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Post> getPosts() {
        if (posts == null) {
            return new ArrayList<>();
        }
        return posts;
    }

    public List<Album> getAlbums() {
        if (albums == null) {
            return new ArrayList<>();
        }
        return albums;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(posts, user.posts) && Objects.equals(albums, user.albums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, posts, albums);
    }
}
