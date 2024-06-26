//package org.example.repository.impl;
//
//import org.example.db.ConnectionManager;
//import org.example.exception.RepositoryException;
//import org.example.model.Post;
//import org.example.model.User;
//import org.example.repository.PostRepository;
//import org.example.repository.UserRepository;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PostRepositoryImpl implements PostRepository {
//    private static final String SAVE_SQL = """
//            INSERT INTO post (content, author_id)
//            VALUES (?, ?);
//            """;
//    private static final String UPDATE_SQL = """
//            UPDATE post
//            SET content = ?
//            WHERE id = ?;
//            """;
//    private static final String DELETE_SQL = """
//            DELETE FROM post
//            WHERE id = ?;
//            """;
//    private static final String FIND_BY_ID_SQL = """
//            SELECT * FROM post
//            WHERE id = ?
//            LIMIT 1;
//            """;
//    private static final String FIND_ALL_SQL = """
//            SELECT * FROM post;
//            """;
//    private static final String FIND_ALL_ALBUM_IDS_BY_POST_ID_SQL = "SELECT album_id FROM post_album WHERE post_id = ?";
//    private ConnectionManager connectionManager;
//    private final UserRepository userRepository;
//
//    public PostRepositoryImpl(ConnectionManager connectionManager, UserRepository userRepository) {
//        this.connectionManager = connectionManager;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Post save(Post post) {
//        try (Connection connection = connectionManager.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
//
//            preparedStatement.setString(1, post.getContent());
//            preparedStatement.setLong(2, post.getAuthor().getId());
//
//            int affectedRows = preparedStatement.executeUpdate();
//            if (affectedRows == 0) {
//                throw new RepositoryException("Failed to save post, no rows affected.");
//            }
//
//            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    post.setId(generatedKeys.getLong(1));
//                } else {
//                    throw new RepositoryException("Failed to save post, no ID obtained.");
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//        return post;
//    }
//
//    @Override
//    public void update(Post post) {
//        try (Connection connection = connectionManager.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
//
//            preparedStatement.setString(1, post.getContent());
//            preparedStatement.setLong(2, post.getId());
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RepositoryException(e);
//        }
//    }
//
//    @Override
//    public boolean deleteById(Long id) {
//        boolean deleteResult;
//        try (Connection connection = connectionManager.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
//            preparedStatement.setLong(1, id);
//            deleteResult = preparedStatement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            throw new RepositoryException(e);
//        }
//        return deleteResult;
//    }
//
//    @Override
//    public Post findById(Long id) {
//        Post post = new Post();
//        try (Connection connection = connectionManager.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                post = createPost(resultSet);
//            }
//
//        } catch (SQLException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//        return post;
//    }
//
//    @Override
//    public List<Post> findAll() {
//        List<Post> posts;
//        try (Connection connection = connectionManager.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//            posts = new ArrayList<>();
//            while (resultSet.next()) {
//                posts.add(createPost(resultSet));
//            }
//        } catch (SQLException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//        return posts;
//    }
//
//    @Override
//    public boolean existsById(Long id) {
//        try (Connection connection = connectionManager.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
//            preparedStatement.setLong(1, id);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                return resultSet.next();
//            }
//        } catch (SQLException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//    }
//
//    @Override
//    public List<Long> getAlbumIds(long postId) {
//        List<Long> albumIds = new ArrayList<>();
//        try (Connection connection = connectionManager.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ALBUM_IDS_BY_POST_ID_SQL)) {
//            preparedStatement.setLong(1, postId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                albumIds.add(resultSet.getLong("album_id"));
//            }
//        } catch (SQLException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//        return albumIds;
//    }
//
//    private Post createPost(ResultSet resultSet) throws SQLException {
//        Post post = new Post();
//        post.setId(resultSet.getLong("id"));
//        post.setContent(resultSet.getString("content"));
//        long authorId = resultSet.getLong("author_id");
//        User user = userRepository.findById(authorId);
//        post.setAuthor(user);
//        return post;
//    }
//}
