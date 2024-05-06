package org.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, length = 256)
    private String title;

    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "author_id", nullable = false)
    private long authorId;


    @ManyToMany(mappedBy = "albums")
 //   @JoinTable(name = "post_album", joinColumns = @JoinColumn(name = "album_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> posts = new ArrayList<>();

    public Album() {
    }

    public Album(long id, String title, String description, long authorId, List<Post> posts) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.posts = posts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        if(posts == null){
            posts = new ArrayList<>();
        }
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id == album.id && authorId == album.authorId && Objects.equals(title, album.title) && Objects.equals(description, album.description) && Objects.equals(posts, album.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, authorId, posts);
    }
}
