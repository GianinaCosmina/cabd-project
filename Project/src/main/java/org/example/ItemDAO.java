package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public void createItem(Item item) throws SQLException {
        String insertItemSql = "INSERT INTO Item (name, description, price) VALUES (?, ?, ?)";
        String insertHistorySql = "INSERT INTO Item_History (name, description, price, t_start) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertItemStmt = conn.prepareStatement(insertItemSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertHistoryStmt = conn.prepareStatement(insertHistorySql)) {

                // Inserare în Item
                insertItemStmt.setString(1, item.getName());
                insertItemStmt.setString(2, item.getDescription());
                insertItemStmt.setDouble(3, item.getPrice());
                insertItemStmt.executeUpdate();

                // Preluare ID generat
                try (ResultSet rs = insertItemStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        item.setId(rs.getInt(1));
                    }
                }

                // Inserare în Item_History
                insertHistoryStmt.setString(1, item.getName());
                insertHistoryStmt.setString(2, item.getDescription());
                insertHistoryStmt.setDouble(3, item.getPrice());
                insertHistoryStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public List<Item> getAllItems() throws SQLException {
        String sql = "SELECT * FROM Item";
//        String sql = "SELECT * FROM cabd_schema.\"Item\";";
        List<Item> items = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item(rs.getString("name"), rs.getString("description"), rs.getDouble("price"));
                item.setId(rs.getInt("id"));
                items.add(item);
            }
        }
        return items;
    }

    public void updateItem(Item item) throws SQLException {
        String updateItemSql = "UPDATE Item SET name = ?, description = ?, price = ? WHERE id = ?";
        String endHistorySql = "UPDATE Item_History SET t_end = CURRENT_TIMESTAMP WHERE id = (SELECT MAX(id) FROM Item_History WHERE name = ? AND t_end IS NULL)";
        String insertHistorySql = "INSERT INTO Item_History (name, description, price, t_start) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement updateItemStmt = conn.prepareStatement(updateItemSql);
                 PreparedStatement endHistoryStmt = conn.prepareStatement(endHistorySql);
                 PreparedStatement insertHistoryStmt = conn.prepareStatement(insertHistorySql)) {

                // Update Item
                updateItemStmt.setString(1, item.getName());
                updateItemStmt.setString(2, item.getDescription());
                updateItemStmt.setDouble(3, item.getPrice());
                updateItemStmt.setInt(4, item.getId());
                updateItemStmt.executeUpdate();

                // End previous history
                endHistoryStmt.setString(1, item.getName());
                endHistoryStmt.executeUpdate();

                // Add new history
                insertHistoryStmt.setString(1, item.getName());
                insertHistoryStmt.setString(2, item.getDescription());
                insertHistoryStmt.setDouble(3, item.getPrice());
                insertHistoryStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Metoda pentru a șterge un item
    public void deleteItem(int itemId) throws SQLException {
        String sql = "DELETE FROM Item WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, itemId);  // Setează ID-ul articolului de șters
            int rowsAffected = statement.executeUpdate();  // Execută interogarea

            if (rowsAffected > 0) {
                System.out.println("Item with ID " + itemId + " deleted successfully.");
            } else {
                System.out.println("No item found with ID " + itemId);
            }
        }
    }
}