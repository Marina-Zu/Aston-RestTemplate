package org.example.repository.impl;

import org.example.db.test.TestConnectionManager;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserRepositoryImplTest {

    static UserRepository userRepository;


    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("db-migration.sql");

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        userRepository = UserRepositoryImpl.getInstance(new TestConnectionManager(postgreSQLContainer));
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void save() {
        User user = new User();
        user.setUsername("username");

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId(), "Saved user should have an ID");
        assertEquals(user.getUsername(), savedUser.getUsername());
    }

    @Test
    void update() {
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        String updatedUsername = "updated_username";
        user.setUsername(updatedUsername);
        userRepository.update(user);

        User updatedUser = userRepository.findById(user.getId());
        assertEquals(updatedUsername, updatedUser.getUsername());
    }

    @Test
    void deleteById() {
        User user = new User();
        user.setUsername("delete_user");
        userRepository.save(user);

        userRepository.deleteById(user.getId());

        assertFalse(userRepository.existsById(user.getId()), "User should be deleted");

    }

    @Test
    void findById() {
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        User foundUser = userRepository.findById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void findAll() {
        User user1 = new User();
        user1.setUsername("user1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user2");
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        assertFalse(userList.isEmpty());
    }

    @Test
    void existsById() {
        User user = new User();
        user.setUsername("exists_user");

        userRepository.save(user);

        assertTrue(userRepository.existsById(user.getId()));
        assertFalse(userRepository.existsById(-1L));
    }

}