package com.inventory.dao;

import com.inventory.model.Item;
import java.util.List;
import java.sql.SQLException;
import java.time.LocalDate;

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
    Item getItemById(Long id) throws SQLException;
    
    /**
     * Insert a new item into the database
     * 
     * @param item the item to insert
     * @return the ID of the newly inserted item
     * @throws SQLException if a database access error occurs
     */
    Long insertItem(Item item) throws SQLException;
    
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
    
    /**
     * Search for items based on multiple criteria
     * 
     * @param description the description to search for (can be null)
     * @param minPrice the minimum price (can be null)
     * @param maxPrice the maximum price (can be null)
     * @param fromDate the start expiration date (can be null)
     * @param toDate the end expiration date (can be null)
     * @param minQuantity the minimum quantity (can be null)
     * @param maxQuantity the maximum quantity (can be null)
     * @return List of items matching the criteria
     * @throws SQLException if a database access error occurs
     */
    List<Item> searchItems(String description, Double minPrice, Double maxPrice, 
                          LocalDate fromDate, LocalDate toDate, 
                          Integer minQuantity, Integer maxQuantity) throws SQLException;
}
