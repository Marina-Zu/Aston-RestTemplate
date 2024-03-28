package org.example.db;

import org.example.exeption.DataValidationException;
import org.example.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {
    private static final String DRIVER_CLASS_KEY = "db.driver-class-name";
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static ConnectionManager connectionManager;

    private ConnectionManagerImpl() {
    }

    public static synchronized ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManagerImpl();
            loadDriver(PropertiesUtil.getProperties(DRIVER_CLASS_KEY));
        }
        return connectionManager;
    }

    private static void loadDriver(String driverClass) {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new DataValidationException("Database driver not loaded");
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.getProperties(URL_KEY),
                PropertiesUtil.getProperties(USERNAME_KEY),
                PropertiesUtil.getProperties(PASSWORD_KEY)
        );
    }
}
