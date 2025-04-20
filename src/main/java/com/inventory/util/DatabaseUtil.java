package com.inventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final Logger logger = Logger.getLogger(DatabaseUtil.class.getName());
    private static final String DB_URL = System.getenv("MYSQL_HOST") + ":" + 
                                       System.getenv("MYSQL_PORT") + "/" + 
                                       System.getenv("MYSQL_DATABASE");
    private static final String DB_USER = System.getenv("MYSQL_USER");
    private static final String DB_PASSWORD = System.getenv("MYSQL_PASSWORD");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL JDBC Driver loaded successfully");
            logger.info("Driver version: " + DriverManager.getDriver("jdbc:mysql://").getMajorVersion() + "." + 
                       DriverManager.getDriver("jdbc:mysql://").getMinorVersion());
        } catch (ClassNotFoundException e) {
            logger.severe("Failed to load MySQL JDBC driver: " + e.getMessage());
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        } catch (SQLException e) {
            logger.severe("Failed to get driver version: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        String connectionUrl = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":" + 
                             System.getenv("MYSQL_PORT") + "/" + 
                             System.getenv("MYSQL_DATABASE") + 
                             "?useSSL=false&allowPublicKeyRetrieval=true";
        logger.info("Attempting to connect to database with URL: " + connectionUrl);
        logger.info("Using username: " + DB_USER);
        
        try {
            Connection conn = DriverManager.getConnection(
                connectionUrl,
                DB_USER,
                DB_PASSWORD
            );
            logger.info("Successfully connected to database");
            logger.info("Database version: " + conn.getMetaData().getDatabaseProductVersion());
            return conn;
        } catch (SQLException e) {
            logger.severe("Failed to connect to database: " + e.getMessage());
            logger.severe("SQL State: " + e.getSQLState());
            logger.severe("Error Code: " + e.getErrorCode());
            throw e;
        }
    }
} 