package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.HikariConnectionManager;
import org.example.exeption.RepositoryException;
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

    private static UserRepository instance;
    private final ConnectionManager connectionManager = HikariConnectionManager.getInstance();

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public User save(User user) {
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
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
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
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
        User user = new User();
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users;
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
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

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        return user;
    }

}
