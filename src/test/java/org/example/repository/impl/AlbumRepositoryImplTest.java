//package org.example.repository.impl;
//
//import org.example.db.test.TestConnectionManager;
//import org.example.model.Album;
//import org.example.model.Post;
//import org.example.model.User;
//import org.example.repository.AbstractRepositoryTest;
//import org.example.repository.AlbumRepository;
//import org.example.repository.PostRepository;
//import org.example.repository.UserRepository;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Testcontainers
//class AlbumRepositoryImplTest extends AbstractRepositoryTest {
//
//    private static AlbumRepository albumRepository;
//    private static UserRepository userRepository;
//    private static PostRepository postRepository;
//    private static User user;
//    private static Album album;
//
//    @BeforeAll
//    static void beforeAll() {
//        postgreSQLContainer.start();
//        TestConnectionManager testConnectionManager = new TestConnectionManager(postgreSQLContainer);
//        userRepository = new UserRepositoryImpl(testConnectionManager);
//        postRepository = new PostRepositoryImpl(testConnectionManager, userRepository);
//        albumRepository = new AlbumRepositoryImpl(testConnectionManager, postRepository);
//    }
//
//    @BeforeEach
//    void beforeEach() {
//        user = new User();
//        user.setUsername("test_user");
//        userRepository.save(user);
//
//        album = new Album();
//        album.setTitle("Test Album");
//        album.setDescription("Test Album Description");
//    }
//
//    @Test
//    void testSaveSuccessful() {
//        album.setAuthorId(user.getId());
//
//        Album savedAlbum = albumRepository.save(album);
//
//        assertNotNull(savedAlbum.getId(), "Saved album should have an ID");
//    }
//
//    @Test
//    void testUpdateSuccessful() {
//        album.setAuthorId(user.getId());
//        album = albumRepository.save(album);
//
//        String updatedTitle = "Updated Album Title";
//        album.setTitle(updatedTitle);
//        albumRepository.update(album);
//
//        Album updatedAlbum = albumRepository.findById(album.getId());
//        assertEquals(updatedTitle, updatedAlbum.getTitle(), "Album title should be updated");
//    }
//
//    @Test
//    void testDeleteByIdSuccessful() {
//        album.setAuthorId(user.getId());
//        album = albumRepository.save(album);
//
//        albumRepository.deleteById(album.getId());
//
//        assertFalse(albumRepository.existsById(album.getId()), "Album should be deleted");
//    }
//
//    @Test
//    void testFindByIdSuccessful() {
//        album.setAuthorId(user.getId());
//        album = albumRepository.save(album);
//
//        Album foundAlbum = albumRepository.findById(album.getId());
//
//        assertNotNull(foundAlbum, "Found album should not be null");
//        assertEquals(album.getTitle(), foundAlbum.getTitle(), "Album title should match");
//        assertEquals(album.getDescription(), foundAlbum.getDescription(), "Album description should match");
//        assertEquals(album.getAuthorId(), foundAlbum.getAuthorId(), "Album author ID should match");
//    }
//
//
//    @Test
//    void testFindAllSuccessful() {
//        album.setAuthorId(user.getId());
//        albumRepository.save(album);
//        Album album2 = new Album();
//        album2.setAuthorId(user.getId());
//        album2.setTitle("Test Album 2");
//
//        albumRepository.save(album2);
//
//        List<Album> albumsList = albumRepository.findAll();
//
//        assertFalse(albumsList.isEmpty());
//    }
//
//
//    @Test
//    void testExistsByIdSuccessful() {
//        album.setAuthorId(user.getId());
//        album = albumRepository.save(album);
//
//        assertTrue(albumRepository.existsById(album.getId()), "Album should exist");
//    }
//
//    @Test
//    void testAddPostSuccessful() {
//        Post post = new Post();
//        post.setContent("Test Post Content");
//        post.setAuthor(user);
//        postRepository.save(post);
//
//        album.setAuthorId(user.getId());
//        albumRepository.save(album);
//
//        albumRepository.addPost(album.getId(), post.getId());
//
//        Album updatedAlbum = albumRepository.findById(album.getId());
//        assertTrue(updatedAlbum.getPosts().contains(post), "Album should contain added post");
//    }
//}