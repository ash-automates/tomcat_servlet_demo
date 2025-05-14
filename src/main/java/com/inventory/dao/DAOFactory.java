package com.inventory.dao;

/**
 * Factory class for creating DAO instances
 */
public class DAOFactory {

    /**
     * Get an instance of ItemDAO
     *
     * @return ItemDAO instance
     */
    public static ItemDAO getItemDAO() {
        return new ItemDAOImpl();
    }

    /**
     * Get an instance of UserDAO
     *
     * @return UserDAO instance
     */
    public static UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
}
