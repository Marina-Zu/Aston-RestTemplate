package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.User;
import org.example.exeption.RepositoryException;
import org.example.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepositoryImpl implements UserRepository {
    private static final String SAVE_SQL = """
            INSERT INTO users (username)
            VALUES (?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET username = ?
            WHERE user_id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE user_id = ?;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT user_id, username FROM users
            WHERE user_id = ?
            LIMIT 1;
            """;
    private static final String FIND_ALL_SQL = """
            SELECT user_id, username FROM users;
            """;
    private static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM users
                        WHERE user_id = ?
                        LIMIT 1);
            """;

    private static UserRepository instance;
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private UserRepositoryImpl() {
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public User save(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("user_id"));
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
    public boolean existById(Long id) {
        return false;
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
    public Optional<User> findById(Long id) {
        User user = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(createUser(resultSet));
            }

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return userList;
    }



    private User createUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("user_id"),
                resultSet.getString("username"),
                null,
                null
        );
    }

}
