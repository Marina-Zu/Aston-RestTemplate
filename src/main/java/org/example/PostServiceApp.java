package org.example;

import org.example.context.AppContext;
import org.example.db.ConnectionManager;
import org.example.db.HikariConnectionManager;
import org.example.repository.impl.AlbumRepositoryImpl;
import org.example.repository.impl.PostRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.impl.AlbumServiceImpl;
import org.example.service.impl.PostServiceImpl;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.mapper.AlbumDtoMapper;
import org.example.servlet.mapper.PostDtoMapper;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.util.InitSql;
import org.mapstruct.factory.Mappers;

public class PostServiceApp {

    public static void initAppContext() {
        UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);
        AlbumDtoMapper albumDtoMapper = Mappers.getMapper(AlbumDtoMapper.class);
        PostDtoMapper postDtoMapper = Mappers.getMapper(PostDtoMapper.class);
        ConnectionManager connectionManager = HikariConnectionManager.getInstance();
        InitSql.initSqlMigration(connectionManager);

        UserRepositoryImpl userRepository = new UserRepositoryImpl(connectionManager);
        PostRepositoryImpl postRepository = new PostRepositoryImpl(connectionManager, userRepository);
        AlbumRepositoryImpl albumRepository = new AlbumRepositoryImpl(connectionManager, postRepository);
        UserServiceImpl userService = new UserServiceImpl(userRepository, userDtoMapper);
        PostServiceImpl postService = new PostServiceImpl(postRepository, postDtoMapper);
        AlbumServiceImpl albumService = new AlbumServiceImpl(albumRepository, albumDtoMapper, postRepository, userRepository);

        AppContext.putBean(UserServiceImpl.class, userService);
        AppContext.putBean(AlbumServiceImpl.class, albumService);
        AppContext.putBean(PostServiceImpl.class, postService);

        AppContext.putBean(PostRepositoryImpl.class, postRepository);
        AppContext.putBean(UserRepositoryImpl.class, userRepository);
        AppContext.putBean(ConnectionManager.class, connectionManager);
        AppContext.putBean(UserDtoMapper.class, userDtoMapper);
        AppContext.putBean(AlbumRepositoryImpl.class, albumRepository);
    }
}
