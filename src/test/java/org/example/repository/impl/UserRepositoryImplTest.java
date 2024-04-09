package org.example.repository.impl;

import org.example.db.test.TestConnectionManager;
import org.example.model.Post;
import org.example.model.User;
import org.example.repository.AbstractRepositoryTest;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserRepositoryImplTest extends AbstractRepositoryTest {

    static UserRepository userRepository;
    static PostRepository postRepository;
    private User user;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        TestConnectionManager testConnectionManager = new TestConnectionManager(postgreSQLContainer);
        userRepository = new UserRepositoryImpl(testConnectionManager);
        postRepository = new PostRepositoryImpl(testConnectionManager, userRepository);
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setUsername("test_user");
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void testSaveSuccessful() {
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId(), "Saved user should have an ID");
        assertEquals(user.getUsername(), savedUser.getUsername());
    }

    @Test
    void testUpdateSuccessful() {
        userRepository.save(user);

        String updatedUsername = "updated_username";
        user.setUsername(updatedUsername);
        userRepository.update(user);

        User updatedUser = userRepository.findById(user.getId());
        assertEquals(updatedUsername, updatedUser.getUsername());
    }

    @Test
    void testDeleteByIdSuccessful() {
        userRepository.save(user);

        userRepository.deleteById(user.getId());

        assertFalse(userRepository.existsById(user.getId()), "User should be deleted");
    }

    @Test
    void testFindByIdSuccessful() {
        userRepository.save(user);

        User foundUser = userRepository.findById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void testFindAllSuccessful() {
        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("user2");
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        assertFalse(userList.isEmpty());
    }

    @Test
    void testFindPostsByUserIdSuccessful() {
        Long userId = 1L;
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(new Post(1L, "Post 1 content", new User(userId, "test_user")));
        expectedPosts.add(new Post(2L, "Post 2 content", new User(userId, "test_user")));

        User user = new User(userId, "test_user");
        userRepository.save(user);

        for (Post post : expectedPosts) {
            postRepository.save(post);
        }
        List<Post> actualPosts = userRepository.findPostsByUserId(userId);

        assertEquals(expectedPosts.size() + 2, actualPosts.size(), "Number of retrieved posts should match");
    }

    @Test
    void testExistsByIdSuccessful() {
        userRepository.save(user);

        assertTrue(userRepository.existsById(user.getId()));
    }


}