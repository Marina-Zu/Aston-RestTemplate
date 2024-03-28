package org.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content", nullable = false, length = 4096)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToMany(mappedBy = "posts")
    private List<Album> comments;

}

