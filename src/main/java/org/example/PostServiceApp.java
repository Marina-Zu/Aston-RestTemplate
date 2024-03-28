package org.example;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.util.InitSql;

public class PostServiceApp {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
        InitSql.initSqlMigration(connectionManager);
    }
}
