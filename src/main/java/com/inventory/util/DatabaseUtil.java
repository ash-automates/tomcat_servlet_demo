package com.inventory.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("inventoryPU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void close() {
        emf.close();
    }
}