package org.example.repository.impl;

import org.example.db.test.TestConnectionManager;
import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.example.repository.AbstractRepositoryTest;
import org.example.repository.AlbumRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class AlbumRepositoryImplTest extends AbstractRepositoryTest {

    private static AlbumRepository albumRepository;
    private static UserRepository userRepository;
    private static PostRepository postRepository;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        TestConnectionManager testConnectionManager = new TestConnectionManager(postgreSQLContainer);
        userRepository = new UserRepositoryImpl(testConnectionManager);
        postRepository = new PostRepositoryImpl(testConnectionManager, userRepository);
        albumRepository = new AlbumRepositoryImpl(testConnectionManager, postRepository);
    }

    @Test
    void save() {
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        Album album = new Album();
        album.setTitle("Test Album");
        album.setDescription("Test Album Description");
        album.setAuthorId(user.getId());

        Album savedAlbum = albumRepository.save(album);

        assertNotNull(savedAlbum.getId(), "Saved album should have an ID");
    }

    @Test
    void update() {
//        // Create user
//        User user = new User();
//        user.setUsername("test_user");
//        userRepository.save(user);
//
//        // Create album
//        Album album = new Album();
//        album.setTitle("Test Album");
//        album.setDescription("Test Album Description");
//        album.setAuthorId(user.getId());
//        album = albumRepository.save(album);
//
//        // Update album
//        String updatedTitle = "Updated Album Title";
//        album.setTitle(updatedTitle);
//        albumRepository.update(album);
//
//        // Check that album is updated
//        Album updatedAlbum = albumRepository.findById(album.getId());
//        assertEquals(updatedTitle, updatedAlbum.getTitle(), "Album title should be updated");
    }

    @Test
    void deleteById() {
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        Album album = new Album();
        album.setTitle("Test Album");
        album.setDescription("Test Album Description");
        album.setAuthorId(user.getId());
        album = albumRepository.save(album);

        albumRepository.deleteById(album.getId());

        assertFalse(albumRepository.existsById(album.getId()), "Album should be deleted");
    }

    @Test
    void findById() {
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        Album album = new Album();
        album.setTitle("Test Album");
        album.setDescription("Test Album Description");
        album.setAuthorId(user.getId());
        album = albumRepository.save(album);

        Album foundAlbum = albumRepository.findById(album.getId());

        assertNotNull(foundAlbum, "Found album should not be null");
        assertEquals(album.getTitle(), foundAlbum.getTitle(), "Album title should match");
        assertEquals(album.getDescription(), foundAlbum.getDescription(), "Album description should match");
        assertEquals(album.getAuthorId(), foundAlbum.getAuthorId(), "Album author ID should match");
    }

    @Test
    void findAll() {
//        // Create user
//        User user = new User();
//        user.setUsername("test_user");
//        userRepository.save(user);
//
//        // Create multiple albums
//        Album album1 = new Album();
//        album1.setTitle("Test Album 1");
//        album1.setDescription("Test Album Description 1");
//        album1.setAuthorId(user.getId());
//        albumRepository.save(album1);
//
//        Album album2 = new Album();
//        album2.setTitle("Test Album 2");
//        album2.setDescription("Test Album Description 2");
//        album2.setAuthorId(user.getId());
//        albumRepository.save(album2);
//
//        // Find all albums
//        List<Album> allAlbums = albumRepository.findAll();
//
//        // Check that all albums are retrieved
//        assertEquals(2, allAlbums.size(), "Number of retrieved albums should match the number of saved albums");
//
//        // Check that all albums have correct author
//        for (Album a : allAlbums) {
//            assertEquals(user.getId(), a.getAuthorId(), "Author of retrieved album should match the expected author");
//        }
    }

    @Test
    void existsById() {
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        Album album = new Album();
        album.setTitle("Test Album");
        album.setDescription("Test Album Description");
        album.setAuthorId(user.getId());
        album = albumRepository.save(album);

        assertTrue(albumRepository.existsById(album.getId()), "Album should exist");
    }

//    @Test
//    void findAllByAuthorId() {
//        User user = new User();
//        user.setUsername("test_user");
//        userRepository.save(user);
//
//        Album album1 = new Album();
//        album1.setTitle("Test Album 1");
//        album1.setDescription("Test Album Description 1");
//        album1.setAuthorId(user.getId());
//        albumRepository.save(album1);
//
//        Album album2 = new Album();
//        album2.setTitle("Test Album 2");
//        album2.setDescription("Test Album Description 2");
//        album2.setAuthorId(user.getId());
//        albumRepository.save(album2);
//
//        List<Album> userAlbums = albumRepository.findAllByAuthorId(user.getId());
//
//        for (Album a : userAlbums) {
//            assertEquals(user.getId(), a.getAuthorId(), "Author ID of retrieved album should match the expected author");
//        }
//    }

    @Test
    void addPost() {
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        Post post = new Post();
        post.setContent("Test Post Content");
        post.setAuthor(user);
        postRepository.save(post);

        Album album = new Album();
        album.setTitle("Test Album");
        album.setDescription("Test Album Description");
        album.setAuthorId(user.getId());
        albumRepository.save(album);

        albumRepository.addPost(album.getId(), post.getId());

        Album updatedAlbum = albumRepository.findById(album.getId());
        assertTrue(updatedAlbum.getPosts().contains(post), "Album should contain added post");
    }

}