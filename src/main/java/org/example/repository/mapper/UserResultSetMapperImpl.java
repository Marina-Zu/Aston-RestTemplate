package org.example.repository.mapper;

import org.example.model.Post;
import org.example.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserResultSetMapperImpl implements UserResultSetMapper {
    @Override
    public User map(ResultSet resultSet) {
        try {
            long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            // Здесь можно извлечь остальные поля из resultSet и использовать их для создания объекта User
            return new User(id, username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
