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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.inventory.model.Item;
import com.inventory.util.DatabaseUtil;

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

        int id = Integer.parseInt(request.getParameter("id"));
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM items WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Item item = new Item(
                            rs.getInt("id"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getDate("expiration_date").toLocalDate(),
                            rs.getInt("quantity")
                        );
                        request.setAttribute("item", item);
                        request.getRequestDispatcher("/items/edit.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("../items");
                    }
                }
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred");
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

        int id = Integer.parseInt(request.getParameter("id"));
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        LocalDate expirationDate = LocalDate.parse(request.getParameter("expirationDate"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "UPDATE items SET description = ?, price = ?, expiration_date = ?, quantity = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, description);
                stmt.setDouble(2, price);
                stmt.setDate(3, java.sql.Date.valueOf(expirationDate));
                stmt.setInt(4, quantity);
                stmt.setInt(5, id);
                stmt.executeUpdate();
            }
            response.sendRedirect("../items");
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to update item");
            request.getRequestDispatcher("/items/edit.jsp").forward(request, response);
        }
    }
} 