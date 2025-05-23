package com.inventory.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.inventory.model.Item;
import com.inventory.dao.DAOFactory;
import com.inventory.dao.ItemDAO;

@WebServlet("/items")
public class ItemsServlet extends HttpServlet {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE; // yyyy-MM-dd
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            ItemDAO itemDAO = DAOFactory.getItemDAO();
            List<Item> items;
            
            // Check if there are search parameters
            if (hasSearchParameters(request)) {
                // Get search parameters from request
                String description = getParameter(request, "description");
                Double minPrice = parseDouble(getParameter(request, "minPrice"));
                Double maxPrice = parseDouble(getParameter(request, "maxPrice"));
                LocalDate fromDate = parseDate(getParameter(request, "fromDate"));
                LocalDate toDate = parseDate(getParameter(request, "toDate"));
                Integer minQuantity = parseInteger(getParameter(request, "minQuantity"));
                Integer maxQuantity = parseInteger(getParameter(request, "maxQuantity"));
                
                // Search with filters
                items = itemDAO.searchItems(description, minPrice, maxPrice, fromDate, toDate, minQuantity, maxQuantity);
                
                // Save search parameters for form re-population
                saveSearchParameters(request);
            } else {
                // Get all items if no search parameters
                items = itemDAO.getAllItems();
            }
            
            request.setAttribute("items", items);
            request.getRequestDispatcher("/items/items.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("/items/items.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }
        
        // Check if this is a new item submission from the new.jsp form
        if (request.getParameter("description") != null && 
            request.getParameter("price") != null && 
            request.getParameter("expirationDate") != null && 
            request.getParameter("quantity") != null) {
            
            try {
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                LocalDate expirationDate = LocalDate.parse(request.getParameter("expirationDate"), DATE_FORMATTER);
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                
                Item item = new Item();
                item.setDescription(description);
                item.setPrice(price);
                item.setExpirationDate(expirationDate);
                item.setQuantity(quantity);
                
                ItemDAO itemDAO = DAOFactory.getItemDAO();
                itemDAO.insertItem(item);
                
                // Redirect to GET request to show all items
                response.sendRedirect("items");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error adding item: " + e.getMessage());
            }
        }
        
        // If not a new item submission or if an error occurred, handle as GET
        doGet(request, response);
    }
    
    // Helper method to check if any search parameters are provided
    private boolean hasSearchParameters(HttpServletRequest request) {
        return request.getParameter("description") != null 
            || request.getParameter("minPrice") != null
            || request.getParameter("maxPrice") != null
            || request.getParameter("fromDate") != null
            || request.getParameter("toDate") != null
            || request.getParameter("minQuantity") != null
            || request.getParameter("maxQuantity") != null;
    }
    
    // Helper method to save search parameters for form re-population
    private void saveSearchParameters(HttpServletRequest request) {
        request.setAttribute("searchDescription", request.getParameter("description"));
        request.setAttribute("searchMinPrice", request.getParameter("minPrice"));
        request.setAttribute("searchMaxPrice", request.getParameter("maxPrice"));
        request.setAttribute("searchFromDate", request.getParameter("fromDate"));
        request.setAttribute("searchToDate", request.getParameter("toDate"));
        request.setAttribute("searchMinQuantity", request.getParameter("minQuantity"));
        request.setAttribute("searchMaxQuantity", request.getParameter("maxQuantity"));
    }
    
    // Helper method to safely get parameter
    private String getParameter(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (value != null && value.trim().isEmpty()) {
            return null;
        }
        return value;
    }
    
    // Helper method to parse Double safely
    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    // Helper method to parse Integer safely
    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    // Helper method to parse LocalDate safely
    private LocalDate parseDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(value, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}