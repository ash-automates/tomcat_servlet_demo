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

import com.inventory.model.Item;
import com.inventory.dao.DAOFactory;
import com.inventory.dao.ItemDAO;

@WebServlet("/items/edit")
public class EditItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("../login");
            return;
        }

        Long id = Long.parseLong(request.getParameter("id"));
        try {
            ItemDAO itemDAO = DAOFactory.getItemDAO();
            Item item = itemDAO.getItemById(id);
            
            if (item != null) {
                request.setAttribute("item", item);
                request.getRequestDispatcher("/items/edit.jsp").forward(request, response);
            } else {
                response.sendRedirect("../items");
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("/items/edit.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("../login");
            return;
        }

        Long id = Long.parseLong(request.getParameter("id"));
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        LocalDate expirationDate = LocalDate.parse(request.getParameter("expirationDate"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        Item item = new Item();
        item.setId(id);
        item.setDescription(description);
        item.setPrice(price);
        item.setExpirationDate(expirationDate);
        item.setQuantity(quantity);

        try {
            ItemDAO itemDAO = DAOFactory.getItemDAO();
            boolean updated = itemDAO.updateItem(item);
            
            if (updated) {
                response.sendRedirect("../items");
            } else {
                request.setAttribute("error", "Failed to update item - item not found");
                request.getRequestDispatcher("/items/edit.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to update item: " + e.getMessage());
            request.getRequestDispatcher("/items/edit.jsp").forward(request, response);
        }
    }
}