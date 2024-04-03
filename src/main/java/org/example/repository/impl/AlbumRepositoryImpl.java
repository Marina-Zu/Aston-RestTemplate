package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.HikariConnectionManager;
import org.example.exception.RepositoryException;
import org.example.model.Album;
import org.example.model.Post;
import org.example.repository.AlbumRepository;
import org.example.repository.PostAlbumRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepositoryImpl implements AlbumRepository {
    private static final String SAVE_SQL = """
            INSERT INTO album (title, description, author_id)
            VALUES (?, ?, ?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE album
            SET title = ?, description = ?
            WHERE id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM album
            WHERE id = ?;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM album
            WHERE id = ?
            LIMIT 1;
            """;
    private static final String FIND_ALL_SQL = """
            SELECT * FROM album;
            """;

    public static final String FIND_ALL_ALBUMS_BY_AUTHOR_ID_SQL = """
            SELECT *
            FROM album
            WHERE author_id = ?;
            """;

    public static final String ADD_POST_TO_ALBUM_SQL = """
            INSERT INTO post_album (album_id, post_id)
            VALUES (?, ?);
            """;

    private static AlbumRepositoryImpl instance;

    private final ConnectionManager connectionManager = HikariConnectionManager.getInstance();
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    private final PostRepository postRepository = PostRepositoryImpl.getInstance();
    private final PostAlbumRepository postAlbumRepository = PostAlbumRepositoryImpl.getInstance();

    public static synchronized AlbumRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new AlbumRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Album save(Album album) {
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, album.getTitle());
            preparedStatement.setString(2, album.getDescription());
            preparedStatement.setLong(3, album.getAuthorId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RepositoryException("Failed to save album, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    album.setId(generatedKeys.getLong(1));
                } else {
                    throw new RepositoryException("Failed to save album, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return album;
    }

    @Override
    public void update(Album album) {
        try (Connection connection = HikariConnectionManager.getInstance().getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
                preparedStatement.setString(1, album.getTitle());
                preparedStatement.setString(2, album.getDescription());
                preparedStatement.setLong(3, album.getId());
                preparedStatement.executeUpdate();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_POST_TO_ALBUM_SQL)) {
                for (Post post : album.getPosts()) {
                    preparedStatement.setLong(1, album.getId());
                    preparedStatement.setLong(2, post.getId());
                    preparedStatement.executeUpdate();
                }
            }

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
    public Album findById(Long id) {
        Album album = new Album();
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                album.setId(resultSet.getLong("id"));
                album.setTitle(resultSet.getString("title"));
                album.setDescription(resultSet.getString("description"));
                album.setAuthorId(resultSet.getLong("author_id"));
            }

            List<Long> postIds = postAlbumRepository.findAllPostIdsByAlbumId(id);

            List<Post> posts = new ArrayList<>();
            for (Long postId : postIds) {
                Post post = postRepository.findById(postId);
                posts.add(post);
            }

            album.setPosts(posts);

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return album;
    }

    @Override
    public List<Album> findAll() {
        List<Album> albums;
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            albums = new ArrayList<>();
            while (resultSet.next()) {
                Album album = new Album();
                album.setId(resultSet.getLong("id"));
                album.setTitle(resultSet.getString("title"));
                album.setDescription(resultSet.getString("description"));
                album.setAuthorId(resultSet.getLong("author_id"));
                albums.add(album);
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

    @Override
    public List<Album> findAllByAuthorId(Long id) {
        List<Album> albums = new ArrayList<>();
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
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
    public void addPost(long albumId, long postId) {
        try (Connection connection = HikariConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_POST_TO_ALBUM_SQL)) {
            preparedStatement.setLong(1, albumId);
            preparedStatement.setLong(2, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
