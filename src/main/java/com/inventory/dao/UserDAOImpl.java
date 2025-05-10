package com.inventory.dao;

import com.inventory.model.User;
import com.inventory.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JDBC implementation of the UserDAO interface
 */
public class UserDAOImpl implements UserDAO {
    
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class.getName());
    
    @Override
    public User findByUsernameAndPassword(String username, String password) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error authenticating user: " + username, e);
            throw e;
        }
        
        return user;
    }
    
    @Override
    public User findByUsername(String username) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding user by username: " + username, e);
            throw e;
        }
        
        return user;
    }
    
    /**
     * Helper method to map a ResultSet row to a User object
     * 
     * @param rs the ResultSet to map
     * @return the mapped User object
     * @throws SQLException if a database access error occurs
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("password")
        );
    }
}
