package org.example;

import org.example.db.ConnectionManager;
import org.example.db.HikariConnectionManager;
import org.example.util.InitSql;

public class PostServiceApp {
    public static void main(String[] args) {
        ConnectionManager connectionManager = HikariConnectionManager.getInstance();
        InitSql.initSqlMigration(connectionManager);
    }
}
