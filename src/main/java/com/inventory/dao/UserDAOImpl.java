package com.inventory.dao;

import com.inventory.model.User;
import jakarta.persistence.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JPA implementation of the UserDAO interface
 */
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAOImpl.class.getName());
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("inventoryPU");

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        EntityManager em = emf.createEntityManager();
        
        try {
            logger.log(Level.INFO, "Executing query with username: " + username.trim() + " and password: " + password.trim());

            // Update logic to validate both username and password
            user = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class)
                     .setParameter("username", username.trim())
                     .setParameter("password", password.trim())
                     .getSingleResult();

            logger.log(Level.INFO, "Query executed successfully. User found: " + user);
        } catch (NoResultException e) {
            logger.log(Level.WARNING, "No user found with the given username and password. Username: " + username.trim());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error authenticating user: " + username.trim(), e);
        } finally {
            em.close();
        }
        
        return user;
    }
    
    @Override
    public User findByUsername(String username) {
        User user = null;
        EntityManager em = emf.createEntityManager();
        
        try {
            user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                     .setParameter("username", username)
                     .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.WARNING, "No user found with the given username.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding user by username: " + username, e);
        } finally {
            em.close();
        }
        
        return user;
    }
    
    /**
     * Method to add a new user to the database
     * 
     * @param user the User object to persist
     */
    public void addUser(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error adding user: " + user.getUsername(), e);
            throw e;
        } finally {
            em.close();
        }
    }
}
