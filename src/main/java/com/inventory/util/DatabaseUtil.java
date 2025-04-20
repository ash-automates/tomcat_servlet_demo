package com.inventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_URL = System.getenv("MYSQL_HOST") + ":" + 
                                       System.getenv("MYSQL_PORT") + "/" + 
                                       System.getenv("MYSQL_DATABASE");
    private static final String DB_USER = System.getenv("MYSQL_USER");
    private static final String DB_PASSWORD = System.getenv("MYSQL_PASSWORD");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://" + DB_URL,
            DB_USER,
            DB_PASSWORD
        );
    }
} 