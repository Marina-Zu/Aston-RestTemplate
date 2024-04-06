package org.example.service.impl;

import org.example.db.ConnectionManager;
import org.example.db.DBConnectionProvider;
import org.example.db.HikariConnectionManager;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;
    @Mock
    private DBConnectionProvider dbConnectionProvider;

    @InjectMocks
    private UserServiceImpl userService;

    @Container
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.3")
            .withInitScript("db-migration.sql");

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() {
//        ConnectionManager connectionProvider = new DBConnectionProvider(
//                postgreSQLContainer.getJdbcUrl(),
//                postgreSQLContainer.getUsername(),
//                postgreSQLContainer.getPassword()
//        );

//        ConnectionManager connectionManager = HikariConnectionManager.getInstance();
//        userService = new UserServiceImpl(connectionManager);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        UserIncomingDto incomingDto = new UserIncomingDto();
        incomingDto.setUsername("New user");
        User user = new User();
        user.setId(1L);
        user.setUsername(incomingDto.getUsername());
        when(userRepository.save(any())).thenReturn(user);

        userService.save(incomingDto);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findById() throws SQLException {

    }

    @Test
    void findAll() throws SQLException {

    }

}