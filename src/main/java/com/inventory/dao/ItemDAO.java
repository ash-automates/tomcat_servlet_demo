package com.inventory.dao;

import com.inventory.model.Item;
import java.util.List;
import java.sql.SQLException;

/**
 * Data Access Object Interface for Item entities
 */
public interface ItemDAO {
    
    /**
     * Retrieve all items from the database
     * 
     * @return List of all items
     * @throws SQLException if a database access error occurs
     */
    List<Item> getAllItems() throws SQLException;
    
    /**
     * Find an item by its ID
     * 
     * @param id the item ID to search for
     * @return the found item or null if not found
     * @throws SQLException if a database access error occurs
     */
    Item getItemById(int id) throws SQLException;
    
    /**
     * Insert a new item into the database
     * 
     * @param item the item to insert
     * @return the ID of the newly inserted item
     * @throws SQLException if a database access error occurs
     */
    int insertItem(Item item) throws SQLException;
    
    /**
     * Update an existing item in the database
     * 
     * @param item the item to update
     * @return true if the update was successful
     * @throws SQLException if a database access error occurs
     */
    boolean updateItem(Item item) throws SQLException;
    
    /**
     * Delete an item from the database
     * 
     * @param id the ID of the item to delete
     * @return true if the delete was successful
     * @throws SQLException if a database access error occurs
     */
    boolean deleteItem(int id) throws SQLException;
}
