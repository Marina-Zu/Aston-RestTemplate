package org.example.util;

import org.example.db.ConnectionManager;
import org.example.exception.DataValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InitSqlMigrationTest {
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private Statement statement;

    @InjectMocks
    private InitSql initSql;

    @BeforeEach
    void setUp() throws SQLException {
        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
    }

    @Test
    void testInitSqlMigrationSuccessfully() throws SQLException {
        InitSql.initSqlMigration(connectionManager);

        verify(statement).execute(anyString());
    }

    @Test
    void testInitSqlMigration_SQLException() throws SQLException {
        doThrow(SQLException.class).when(statement).execute(anyString());

        assertThrows(DataValidationException.class, () -> InitSql.initSqlMigration(connectionManager));

        verify(connectionManager).getConnection();
        verify(connection).createStatement();
        verify(statement).execute(anyString());
    }
}