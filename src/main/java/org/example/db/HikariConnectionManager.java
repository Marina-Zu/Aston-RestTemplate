package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionManager implements ConnectionManager {
    private static final String DRIVER_CLASS_KEY = "db.driver-class-name";
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private static HikariConnectionManager instance = new HikariConnectionManager();

    private static HikariDataSource dataSource;

    private static HikariConfig config = new HikariConfig();

    static {
        config.setDriverClassName(PropertiesUtil.getProperties(DRIVER_CLASS_KEY));
        config.setJdbcUrl(PropertiesUtil.getProperties(URL_KEY));
        config.setUsername(PropertiesUtil.getProperties(USERNAME_KEY));
        config.setPassword(PropertiesUtil.getProperties(PASSWORD_KEY));
        dataSource = new HikariDataSource(config);
    }

    public static ConnectionManager getInstance() {
        return instance;
    }

    private HikariConnectionManager() {
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
