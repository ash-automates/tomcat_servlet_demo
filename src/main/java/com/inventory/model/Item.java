package com.inventory.model;

import java.time.LocalDate;

public class Item {
    private int id;
    private String description;
    private double price;
    private LocalDate expirationDate;
    private int quantity;

    public Item() {}

    public Item(int id, String description, double price, LocalDate expirationDate, int quantity) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
} 