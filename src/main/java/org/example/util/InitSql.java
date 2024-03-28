package org.example.util;

import org.example.db.ConnectionManager;
import org.example.exeption.DataValidationException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class InitSql {
    private static final String DB_MIGRATION = "db-migration.sql";
    private static String dbMigration;

    static {
        loadInitSql();
    }

    private InitSql() {
    }

    public static void initSqlMigration(ConnectionManager connectionManager) {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(dbMigration);
        } catch (SQLException e) {
            throw new DataValidationException(e.getMessage());
        }
    }

    private static void loadInitSql() {
        try (InputStream inFile = InitSql.class.getClassLoader().getResourceAsStream(DB_MIGRATION)) {
            dbMigration = new String(inFile.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new DataValidationException(e.getMessage());
        }
    }
}
