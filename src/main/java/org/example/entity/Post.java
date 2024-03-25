package org.example.entity;


import jakarta.persistence.*;

import java.util.List;
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "content", nullable = false, length = 4096)
    private String content;
    @Column(name = "author_id")
    private long authorId;
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Album> comments;
}

