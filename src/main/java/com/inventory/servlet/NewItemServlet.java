package com.inventory.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/items/new")
public class NewItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("../login");
            return;
        }
        
        request.getRequestDispatcher("/items/new.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("../login");
            return;
        }

        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String expirationDateStr = request.getParameter("expirationDate");
        String quantityStr = request.getParameter("quantity");

        try {
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            java.time.LocalDate expirationDate = java.time.LocalDate.parse(expirationDateStr);

            com.inventory.model.Item item = new com.inventory.model.Item();
            item.setDescription(description);
            item.setPrice(price);
            item.setExpirationDate(expirationDate);
            item.setQuantity(quantity);

            com.inventory.dao.ItemDAO itemDAO = com.inventory.dao.DAOFactory.getItemDAO();
            itemDAO.insertItem(item);

            response.sendRedirect("../items");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to add item: " + e.getMessage());
            request.getRequestDispatcher("/items/new.jsp").forward(request, response);
        }
    }
}