package com.inventory.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import com.inventory.dao.DAOFactory;
import com.inventory.dao.ItemDAO;

@WebServlet("/items/delete")
public class DeleteItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("../login");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            ItemDAO itemDAO = DAOFactory.getItemDAO();
            boolean deleted = itemDAO.deleteItem(id);
            
            if (!deleted) {
                request.setAttribute("error", "Failed to delete item - item not found");
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to delete item: " + e.getMessage());
        }
        response.sendRedirect("../items");
    }
} 