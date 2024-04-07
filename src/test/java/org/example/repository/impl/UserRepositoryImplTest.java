package org.example.repository.impl;

import org.example.db.test.TestConnectionManager;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        user.setUsername("test_user");

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId(), "Saved user should have an ID");
        assertEquals(user.getUsername(), savedUser.getUsername());
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void existsById() {
    }

}