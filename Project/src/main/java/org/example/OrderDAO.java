package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private boolean customerExists(int customerId, Connection conn) throws SQLException {
        String checkCustomerSql = "SELECT 1 FROM customer WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(checkCustomerSql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // True dacă există, false dacă nu există
            }
        }
    }

    private boolean itemExists(int itemId, Connection conn) throws SQLException {
        String checkItemSql = "SELECT 1 FROM item WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(checkItemSql)) {
            stmt.setInt(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // True dacă există, false dacă nu există
            }
        }
    }

    public void createOrder(Order order) throws SQLException {
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        try (Connection conn = DatabaseManager.getConnection()) {
            if (!customerExists(order.getCustomerId(), conn)) {
                throw new SQLException("Customer with ID " + order.getCustomerId() + " does not exist.");
            }

            if (!itemExists(order.getItemId(), conn)) {
                throw new SQLException("Item with ID " + order.getItemId() + " does not exist.");
            }

            String insertOrderSql = "INSERT INTO customer_order (order_id, item_id, customer_id, quantity, comments) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement insertOrderStmt = conn.prepareStatement(insertOrderSql)) {
                insertOrderStmt.setInt(1, order.getOrderId());
                insertOrderStmt.setInt(2, order.getItemId());
                insertOrderStmt.setInt(3, order.getCustomerId());
                insertOrderStmt.setInt(4, order.getQuantity());
                insertOrderStmt.setString(5, order.getComments());

                insertOrderStmt.executeUpdate();
            }
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        String sql = "SELECT * FROM customer_order";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("item_id"),
                        rs.getInt("quantity"),
                        rs.getString("comments")
                );
                orders.add(order);
            }
        }
        return orders;
    }

    public void updateOrder(Order order) throws SQLException {
        String checkExistenceSql = "SELECT 1 FROM customer_order WHERE order_id = ? AND item_id = ?";
        String updateOrderSql = "UPDATE customer_order SET quantity = ?, comments = ? WHERE order_id = ? AND item_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            if (!customerExists(order.getCustomerId(), conn)) {
                throw new SQLException("Customer with ID " + order.getCustomerId() + " does not exist.");
            }

            if (!itemExists(order.getItemId(), conn)) {
                throw new SQLException("Item with ID " + order.getItemId() + " does not exist.");
            }

            try (PreparedStatement checkStmt = conn.prepareStatement(checkExistenceSql)) {
                checkStmt.setInt(1, order.getOrderId());
                checkStmt.setInt(2, order.getItemId());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Order with orderId " + order.getOrderId() + " and itemId " + order.getItemId() + " does not exist.");
                    }
                }
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateOrderSql)) {
                updateStmt.setInt(1, order.getQuantity());
                updateStmt.setString(2, order.getComments());
                updateStmt.setInt(3, order.getOrderId());
                updateStmt.setInt(4, order.getItemId());
                updateStmt.executeUpdate();
            }
        }
    }

    public void deleteOrder(int orderId) throws SQLException {
        String deleteOrderItemsSql = "DELETE FROM customer_order WHERE order_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            String checkOrderSql = "SELECT 1 FROM customer_order WHERE order_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkOrderSql)) {
                checkStmt.setInt(1, orderId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("No such order found for the given orderId.");
                    }
                }
            }

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteOrderItemsSql)) {
                deleteStmt.setInt(1, orderId);
                deleteStmt.executeUpdate();
            }
        }
    }

    public void deleteOrderItem(int orderId, int itemId) throws SQLException {
        String deleteOrderItemSql = "DELETE FROM customer_order WHERE order_id = ? AND item_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            if (!customerExists(orderId, conn)) {
                throw new SQLException("Order with ID " + orderId + " does not exist.");
            }

            if (!itemExists(itemId, conn)) {
                throw new SQLException("Item with ID " + itemId + " does not exist.");
            }

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteOrderItemSql)) {
                deleteStmt.setInt(1, orderId);
                deleteStmt.setInt(2, itemId);
                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("No such order item found for the given orderId and itemId.");
                }
            }
        }
    }
}
