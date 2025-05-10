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
import java.util.List;

import com.inventory.model.Item;
import com.inventory.dao.DAOFactory;
import com.inventory.dao.ItemDAO;

@WebServlet("/items")
public class ItemsServlet extends HttpServlet {
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
            List<Item> items = itemDAO.getAllItems();
            
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

        try {
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            LocalDate expirationDate = LocalDate.parse(request.getParameter("expirationDate"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            Item newItem = new Item();
            newItem.setDescription(description);
            newItem.setPrice(price);
            newItem.setExpirationDate(expirationDate);
            newItem.setQuantity(quantity);
            
            ItemDAO itemDAO = DAOFactory.getItemDAO();
            itemDAO.insertItem(newItem);
            
            response.sendRedirect("items");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/items/new.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/items/new.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/items/new.jsp").forward(request, response);
        }
    }
} 