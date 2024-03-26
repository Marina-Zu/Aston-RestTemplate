package org.example.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname", nullable = false, length = 256)
    private String firstName;

    @Column(name = "lastname", length = 256)
    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @ManyToMany(mappedBy = "albums")
    private List<Album> albums;

}
