//package org.example.repository.impl;
//
//import org.example.db.test.TestConnectionManager;
//import org.example.model.Post;
//import org.example.model.User;
//import org.example.repository.AbstractRepositoryTest;
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
//class PostRepositoryImplTest extends AbstractRepositoryTest {
//    static PostRepository postRepository;
//    static UserRepository userRepository;
//    static Post post;
//    static User user;
//
//    @BeforeAll
//    static void beforeAll() {
//        postgreSQLContainer.start();
//        TestConnectionManager testConnectionManager = new TestConnectionManager(postgreSQLContainer);
//        userRepository = new UserRepositoryImpl(testConnectionManager);
//        postRepository = new PostRepositoryImpl(testConnectionManager, userRepository);
//    }
//
//    @BeforeEach
//    void beforeEach() {
//        user = new User();
//        user.setUsername("test_user");
//        userRepository.save(user);
//
//        post = new Post();
//        post.setContent("Test content");
//        post.setAuthor(user);
//    }
//
//    @Test
//    void testSaveSuccessful() {
//        Post savedPost = postRepository.save(post);
//
//        assertNotNull(savedPost.getId(), "Saved post should have an ID");
//        assertEquals(post.getContent(), savedPost.getContent());
//
//        assertEquals(user.getId(), savedPost.getAuthor().getId(), "Saved post should be associated with the correct user");
//    }
//
//    @Test
//    void testUpdateSuccessful() {
//        postRepository.save(post);
//
//        String updatedContent = "Updated content";
//        post.setContent(updatedContent);
//        postRepository.update(post);
//
//        Post updatedPost = postRepository.findById(post.getId());
//        assertEquals(updatedContent, updatedPost.getContent(), "Post content should be updated");
//    }
//
//    @Test
//    void testDeleteByIdSuccessful() {
//        postRepository.save(post);
//
//        postRepository.deleteById(post.getId());
//
//        assertFalse(postRepository.existsById(post.getId()), "Post should be deleted");
//    }
//
//    @Test
//    void testFindByIdSuccessful() {
//        postRepository.save(post);
//
//        Post foundPost = postRepository.findById(post.getId());
//        assertNotNull(foundPost, "Found post should not be null");
//        assertEquals(post.getContent(), foundPost.getContent(), "Content of found post should match");
//    }
//
//    @Test
//    void testFindAllSuccessful() {
//        postRepository.save(post);
//
//        List<Post> postList = postRepository.findAll();
//
//        assertFalse(postList.isEmpty());
//    }
//
//
//    @Test
//    void testExistsByIdSuccessful() {
//        postRepository.save(post);
//
//        assertTrue(postRepository.existsById(post.getId()), "Post should exist");
//    }
//
//    @Test
//     void testEmptyAlbumIds() {
//        long postId = 1L;
//        List<Long> expectedAlbumIds = List.of();
//
//        List<Long> albumIds = postRepository.getAlbumIds(postId);
//
//        assertEquals(expectedAlbumIds, albumIds);
//    }
//
//}