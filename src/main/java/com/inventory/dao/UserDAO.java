package com.inventory.dao;

import com.inventory.model.User;
import java.sql.SQLException;

/**
 * Data Access Object Interface for User entities
 */
public interface UserDAO {
    
    /**
     * Find a user by username and password
     * 
     * @param username the username to search for
     * @param password the password to match
     * @return the found User, or null if no matching user exists
     * @throws SQLException if a database access error occurs
     */
    User findByUsernameAndPassword(String username, String password) throws SQLException;
    
    /**
     * Find a user by username
     * 
     * @param username the username to search for
     * @return the found User, or null if no matching user exists
     * @throws SQLException if a database access error occurs
     */
    User findByUsername(String username) throws SQLException;
}
