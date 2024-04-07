//package org.example.service.impl;
//
//import org.example.db.ConnectionManager;
//import org.example.db.DBConnectionProvider;
//import org.example.db.HikariConnectionManager;
//import org.example.db.test.TestConnectionManager;
//import org.example.model.User;
//import org.example.repository.UserRepository;
//import org.example.repository.impl.UserRepositoryImpl;
//import org.example.servlet.dto.UserIncomingDto;
//import org.example.servlet.mapper.UserDtoMapper;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.sql.SQLException;
//
//import static org.mockito.Mockito.*;
//
//@Testcontainers
//@ExtendWith(MockitoExtension.class)
//class UserRepositoryImplTest {
//
//    static UserRepository userRepository;
//
//    @Container
//    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("test_db")
//            .withUsername("test")
//            .withPassword("test")
//            .withInitScript("db-migration.sql");
//
//    @BeforeAll
//    static void beforeAll() {
//        postgreSQLContainer.start();
//        userRepository = UserRepositoryImpl.getInstance(new TestConnectionManager(postgreSQLContainer));
//    }
//
//    @AfterAll
//    static void afterAll() {
//        postgreSQLContainer.stop();
//    }
//
//    @Test
//    void save() {
//        UserIncomingDto incomingDto = new UserIncomingDto();
//        incomingDto.setUsername("New user");
//        User user = new User();
//        user.setId(1L);
//        user.setUsername(incomingDto.getUsername());
//        when(userRepository.save(any())).thenReturn(user);
//
//        userService.save(incomingDto);
//
//        verify(userRepository, times(1)).save(any());
//    }
//
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void deleteById() {
//    }
//
//    @Test
//    void findById() {
//    }
//
//    @Test
//    void findAll() {
//    }
//}