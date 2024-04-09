package org.example.repository.impl;

import org.example.db.test.TestConnectionManager;
import org.example.model.Post;
import org.example.model.User;
import org.example.repository.AbstractRepositoryTest;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class PostRepositoryImplTest extends AbstractRepositoryTest {
    static PostRepository postRepository;
    static UserRepository userRepository;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        TestConnectionManager testConnectionManager = new TestConnectionManager(postgreSQLContainer);
        userRepository = new UserRepositoryImpl(testConnectionManager);
        postRepository = new PostRepositoryImpl(testConnectionManager, userRepository);
    }

//        @AfterEach
//    void beforeEach() {
//        clearPostData();
//
//    }

//    private void clearPostData() {
//        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
//                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
//             Statement statement = connection.createStatement()) {
//            statement.executeUpdate("TRUNCATE TABLE post RESTART IDENTITY CASCADE");
//        } catch (SQLException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//    }
    @Test
    void save() {
        // Создаем пользователя и сохраняем его
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        // Создаем пост и сохраняем его
        Post post = new Post();
        post.setContent("Test content");
        post.setAuthor(user);
        Post savedPost = postRepository.save(post);

        // Проверяем, что пост успешно сохранен
        assertNotNull(savedPost.getId(), "Saved post should have an ID");
        assertEquals(post.getContent(), savedPost.getContent());

        // Проверяем, что пост связан с правильным пользователем
        assertEquals(user.getId(), savedPost.getAuthor().getId(), "Saved post should be associated with the correct user");
    }

    @Test
    void update() {
        // Создаем пользователя и сохраняем его
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        // Создаем пост и сохраняем его
        Post post = new Post();
        post.setContent("Test content");
        post.setAuthor(user);
        postRepository.save(post);

        // Изменяем контент поста
        String updatedContent = "Updated content";
        post.setContent(updatedContent);
        postRepository.update(post);

        // Проверяем, что пост успешно обновлен
        Post updatedPost = postRepository.findById(post.getId());
        assertEquals(updatedContent, updatedPost.getContent(), "Post content should be updated");
    }

    @Test
    void deleteById() {
        // Создаем пользователя и сохраняем его
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        // Создаем пост и сохраняем его
        Post post = new Post();
        post.setContent("Test content");
        post.setAuthor(user);
        postRepository.save(post);

        // Удаляем пост по ID
        postRepository.deleteById(post.getId());

        // Проверяем, что пост удален
        assertFalse(postRepository.existsById(post.getId()), "Post should be deleted");
    }

    @Test
    void findById() {
        // Создаем пользователя и сохраняем его
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        // Создаем пост и сохраняем его
        Post post = new Post();
        post.setContent("Test content");
        post.setAuthor(user);
        postRepository.save(post);

        // Получаем пост по ID и проверяем
        Post foundPost = postRepository.findById(post.getId());
        assertNotNull(foundPost, "Found post should not be null");
        assertEquals(post.getContent(), foundPost.getContent(), "Content of found post should match");
    }

    @Test
    void findAll() {
//        // Создаем пользователя и сохраняем его
//        User user = new User();
//        user.setUsername("test_user");
//        userRepository.save(user);
//
//        // Создаем несколько постов и сохраняем их
//        Post post1 = new Post();
//        post1.setContent("Test content 1");
//        post1.setAuthor(user);
//        postRepository.save(post1);
//
//        Post post2 = new Post();
//        post2.setContent("Test content 2");
//        post2.setAuthor(user);
//        postRepository.save(post2);
//
//        // Получаем все посты и проверяем их количество
//        List<Post> allPosts = postRepository.findAll();
//        assertEquals(10, allPosts.size(), "Number of retrieved posts should match the number of saved posts");
//
//        // Проверяем, что все посты имеют правильного автора
//        for (Post p : allPosts) {
//            assertEquals(user.getId(), p.getAuthor().getId(), "Author of retrieved post should match the expected author");
//        }
    }

    @Test
    void existsById() {
        // Создаем пользователя и сохраняем его
        User user = new User();
        user.setUsername("test_user");
        userRepository.save(user);

        // Создаем пост и сохраняем его
        Post post = new Post();
        post.setContent("Test content");
        post.setAuthor(user);
        postRepository.save(post);

        // Проверяем существование поста по ID
        assertTrue(postRepository.existsById(post.getId()), "Post should exist");
    }

    @Test
    void findAllByAuthorId() {
    }
}