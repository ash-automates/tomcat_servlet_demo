package com.inventory.dao;

/**
 * Factory class for creating DAO instances
 */
public class DAOFactory {
    
    private static ItemDAO itemDAO;
    private static UserDAO userDAO;
    
    /**
     * Get an instance of ItemDAO
     * 
     * @return ItemDAO instance
     */
    public static ItemDAO getItemDAO() {
        if (itemDAO == null) {
            itemDAO = new ItemDAOImpl();
        }
        return itemDAO;
    }
    
    /**
     * Get an instance of UserDAO
     * 
     * @return UserDAO instance
     */
    public static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }
}
