package com.inventory.dao;

import com.inventory.model.Item;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

/**
 * JPA implementation of the ItemDAO interface
 */
public class ItemDAOImpl implements ItemDAO {

    private static final Logger logger = Logger.getLogger(ItemDAOImpl.class.getName());
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("inventoryPU");

    @Override
    public List<Item> getAllItems() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving all items", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Item getItemById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Item.class, id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving item with ID: " + id, e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Long insertItem(Item item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(item);
            transaction.commit();
            return item.getId();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error inserting item", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateItem(Item item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(item);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error updating item with ID: " + item.getId(), e);
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteItem(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Item item = em.find(Item.class, id);
            if (item != null) {
                em.remove(item);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Item> searchItems(String description, Double minPrice, Double maxPrice,
                                 LocalDate fromDate, LocalDate toDate,
                                 Integer minQuantity, Integer maxQuantity) {
        EntityManager em = emf.createEntityManager();
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT i FROM Item i WHERE 1=1");
            Map<String, Object> parameters = new HashMap<>();

            // Add description filter
            if (description != null && !description.trim().isEmpty()) {
                queryBuilder.append(" AND LOWER(i.description) LIKE :description");
                parameters.put("description", "%" + description.toLowerCase() + "%");
            }

            // Add price range filters
            if (minPrice != null) {
                queryBuilder.append(" AND i.price >= :minPrice");
                parameters.put("minPrice", minPrice);
            }

            if (maxPrice != null) {
                queryBuilder.append(" AND i.price <= :maxPrice");
                parameters.put("maxPrice", maxPrice);
            }

            // Add expiration date range filters
            if (fromDate != null) {
                queryBuilder.append(" AND i.expirationDate >= :fromDate");
                parameters.put("fromDate", fromDate);
            }

            if (toDate != null) {
                queryBuilder.append(" AND i.expirationDate <= :toDate");
                parameters.put("toDate", toDate);
            }

            // Add quantity range filters
            if (minQuantity != null) {
                queryBuilder.append(" AND i.quantity >= :minQuantity");
                parameters.put("minQuantity", minQuantity);
            }

            if (maxQuantity != null) {
                queryBuilder.append(" AND i.quantity <= :maxQuantity");
                parameters.put("maxQuantity", maxQuantity);
            }

            // Create and configure the query
            TypedQuery<Item> query = em.createQuery(queryBuilder.toString(), Item.class);

            // Set parameters
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            return query.getResultList();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching for items", e);
            throw e;
        } finally {
            em.close();
        }
    }
}
