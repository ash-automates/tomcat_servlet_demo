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
import java.util.ArrayList;
import java.util.List;

import com.inventory.model.Item;
import com.inventory.util.DatabaseUtil;

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

        try (Connection conn = DatabaseUtil.getConnection()) {
            List<Item> items = new ArrayList<>();
            String sql = "SELECT * FROM items ORDER BY id";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getDate("expiration_date").toLocalDate(),
                        rs.getInt("quantity")
                    );
                    items.add(item);
                }
            }
            request.setAttribute("items", items);
            request.getRequestDispatcher("/items.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred");
            request.getRequestDispatcher("/items.jsp").forward(request, response);
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

        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        LocalDate expirationDate = LocalDate.parse(request.getParameter("expirationDate"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO items (description, price, expiration_date, quantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, description);
                stmt.setDouble(2, price);
                stmt.setDate(3, java.sql.Date.valueOf(expirationDate));
                stmt.setInt(4, quantity);
                stmt.executeUpdate();
            }
            response.sendRedirect("items");
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to add item");
            request.getRequestDispatcher("/items.jsp").forward(request, response);
        }
    }
} 