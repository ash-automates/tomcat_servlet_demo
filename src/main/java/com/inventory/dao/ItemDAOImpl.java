package com.inventory.dao;

import com.inventory.model.Item;
import com.inventory.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JDBC implementation of the ItemDAO interface
 */
public class ItemDAOImpl implements ItemDAO {
    
    private static final Logger logger = Logger.getLogger(ItemDAOImpl.class.getName());
    
    @Override
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Item item = mapResultSetToItem(rs);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving all items", e);
            throw e;
        }
        
        return items;
    }
    
    @Override
    public Item getItemById(int id) throws SQLException {
        Item item = null;
        String sql = "SELECT * FROM items WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    item = mapResultSetToItem(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving item with ID: " + id, e);
            throw e;
        }
        
        return item;
    }
    
    @Override
    public int insertItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (description, price, expiration_date, quantity) VALUES (?, ?, ?, ?)";
        int generatedId = -1;
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, item.getDescription());
            stmt.setDouble(2, item.getPrice());
            stmt.setDate(3, java.sql.Date.valueOf(item.getExpirationDate()));
            stmt.setInt(4, item.getQuantity());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error inserting item", e);
            throw e;
        }
        
        return generatedId;
    }
    
    @Override
    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE items SET description = ?, price = ?, expiration_date = ?, quantity = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, item.getDescription());
            stmt.setDouble(2, item.getPrice());
            stmt.setDate(3, java.sql.Date.valueOf(item.getExpirationDate()));
            stmt.setInt(4, item.getQuantity());
            stmt.setInt(5, item.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating item with ID: " + item.getId(), e);
            throw e;
        }
    }
    
    @Override
    public boolean deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting item with ID: " + id, e);
            throw e;
        }
    }
    
    /**
     * Helper method to map a ResultSet row to an Item object
     * 
     * @param rs the ResultSet to map
     * @return the mapped Item object
     * @throws SQLException if a database access error occurs
     */
    private Item mapResultSetToItem(ResultSet rs) throws SQLException {
        return new Item(
            rs.getInt("id"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getDate("expiration_date").toLocalDate(),
            rs.getInt("quantity")
        );
    }
}
