package com.inventory.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inventory.util.DatabaseUtil;

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
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "DELETE FROM items WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to delete item");
        }
        response.sendRedirect("../items");
    }
} 