package org.example.repository.impl;

import org.example.db.HikariConnectionManager;
import org.example.exception.RepositoryException;
import org.example.repository.PostAlbumRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostAlbumRepositoryImpl implements PostAlbumRepository {
    private static final String FIND_ALL_POST_IDS_BY_ALBUM_ID_SQL = "SELECT post_id FROM post_album WHERE album_id = ?";
    private static PostAlbumRepository instance;

    public static synchronized PostAlbumRepository getInstance() {
        if (instance == null) {
            instance = new PostAlbumRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Long> findAllPostIdsByAlbumId(Long albumId) {
        List<Long> postIds = new ArrayList<>();
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_POST_IDS_BY_ALBUM_ID_SQL)) {
            preparedStatement.setLong(1, albumId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                postIds.add(resultSet.getLong("post_id"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return postIds;
    }
}

