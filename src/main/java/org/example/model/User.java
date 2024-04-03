package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.example.repository.AlbumRepository;
import org.example.repository.PostRepository;
import org.example.repository.impl.AlbumRepositoryImpl;
import org.example.repository.impl.PostRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    private static final PostRepository postRepository = PostRepositoryImpl.getInstance();
    private static final AlbumRepository albumRepository = AlbumRepositoryImpl.getInstance();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @OneToMany(mappedBy = "author")
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

    public void addPost(Post post) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(post);
    }

    public void addAlbum(Album album) {
        if (albums == null) {
            albums = new ArrayList<>();
        }
        albums.add(album);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Post> getPosts() {
        if (posts == null) {
            this.posts = postRepository.findAllByAuthorId(this.id);
        }
        return posts;
    }

    public List<Album> getAlbums() {
        if (albums == null) {
            this.albums = albumRepository.findAllByAuthorId(this.id);
        }
        return albums;
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
