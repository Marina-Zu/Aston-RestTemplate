package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.exception.RepositoryException;
import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImpl implements UserRepository {
    private static final String SAVE_SQL = """
            INSERT INTO users (username)
            VALUES (?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET username = ?
            WHERE id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM users
            WHERE id = ?
            LIMIT 1;
            """;
    private static final String FIND_ALL_SQL = """
            SELECT * FROM users;
            """;

    public static final String FIND_ALL_POSTS_BY_AUTHOR_ID_SQL = """
            SELECT *
            FROM post
            WHERE author_id = ?;
            """;


    public static final String FIND_ALL_ALBUMS_BY_AUTHOR_ID_SQL = """
            SELECT *
            FROM album
            WHERE author_id = ?;
            """;

    private ConnectionManager connectionManager;

    public UserRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public User save(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return user;
    }

    @Override
    public void update(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return deleteResult;
    }

    @Override
    public User findById(Long id) {
        User user = null;
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return users;
    }

    @Override
    public List<Post> findPostsByUserId(Long id) {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_POSTS_BY_AUTHOR_ID_SQL)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setId(resultSet.getLong("id"));
                    post.setContent(resultSet.getString("content"));
                    User author = findById(id);
                    post.setAuthor(author);
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return posts;
    }

    @Override
    public List<Album> findAllByAuthorId(Long id) {
        List<Album> albums = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ALBUMS_BY_AUTHOR_ID_SQL)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Album album = new Album();
                    album.setId(resultSet.getLong("id"));
                    album.setTitle(resultSet.getString("title"));
                    album.setDescription(resultSet.getString("description"));
                    album.setAuthorId(id);
                    albums.add(album);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return albums;
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        return user;
    }

    private Post createPost(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getLong("id"));
        post.setContent(resultSet.getString("content"));
        return post;
    }
}
