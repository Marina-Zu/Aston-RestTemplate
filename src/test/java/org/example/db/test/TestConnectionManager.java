package org.example.db.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.db.ConnectionManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnectionManager implements ConnectionManager {
    private HikariDataSource dataSource;
    private HikariConfig config = new HikariConfig();

    public TestConnectionManager(PostgreSQLContainer<?> postgreSQLContainer) {
        config.setDriverClassName(postgreSQLContainer.getDriverClassName());
        config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
        config.setUsername(postgreSQLContainer.getUsername());
        config.setPassword(postgreSQLContainer.getPassword());
        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
