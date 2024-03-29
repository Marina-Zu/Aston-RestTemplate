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
            return new User(id, username); // Предположим, что User имеет конструктор с полями id и username
        } catch (SQLException e) {
            // Обработка ошибки, например, выброс исключения или возврат null
            e.printStackTrace(); // Выводим стек вызовов для отладки
            return null; // Возвращаем null в случае ошибки
        }
    }
}
