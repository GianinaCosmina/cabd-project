package org.example.dao;

import org.example.DatabaseManager;
import org.example.model.Item;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDAO {
    public void createItem(Item item) throws SQLException {
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        if (item.getDescription() == null) {
            throw new IllegalArgumentException("Item description cannot be null.");
        }
        if (item.getPrice() <= 0) {
            throw new IllegalArgumentException("Item price must be greater than 0.");
        }

        String insertItemSql = "INSERT INTO Item (name, description, price) VALUES (?, ?, ?)";
        String insertHistorySql = "INSERT INTO Item_History (item_id, name, description, price, t_start) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertItemStmt = conn.prepareStatement(insertItemSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertHistoryStmt = conn.prepareStatement(insertHistorySql)) {

                // insert item
                insertItemStmt.setString(1, item.getName());
                insertItemStmt.setString(2, item.getDescription());
                insertItemStmt.setDouble(3, item.getPrice());
                insertItemStmt.executeUpdate();

                try (ResultSet rs = insertItemStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        item.setId(rs.getInt(1)); // Setăm ID-ul generat
                    }
                }

                // insert into history
                insertHistoryStmt.setInt(1, item.getId());
                insertHistoryStmt.setString(2, item.getName());
                insertHistoryStmt.setString(3, item.getDescription());
                insertHistoryStmt.setDouble(4, item.getPrice());
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
        String checkExistenceSql = "SELECT 1 FROM Item WHERE id = ?";
        String updateItemSql = "UPDATE Item SET name = ?, description = ?, price = ? WHERE id = ?";
        String endHistorySql = "UPDATE Item_History SET t_end = CURRENT_TIMESTAMP WHERE item_id = ? AND t_end IS NULL";
        String insertHistorySql = "INSERT INTO Item_History (item_id, name, description, price, t_start) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkExistenceSql)) {
                checkStmt.setInt(1, item.getId());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Item with ID " + item.getId() + " does not exist.");
                    }
                }
            }

            try (PreparedStatement updateItemStmt = conn.prepareStatement(updateItemSql);
                 PreparedStatement endHistoryStmt = conn.prepareStatement(endHistorySql);
                 PreparedStatement insertHistoryStmt = conn.prepareStatement(insertHistorySql)) {

                // update item
                updateItemStmt.setString(1, item.getName());
                updateItemStmt.setString(2, item.getDescription());
                updateItemStmt.setDouble(3, item.getPrice());
                updateItemStmt.setInt(4, item.getId());
                updateItemStmt.executeUpdate();

                // update history
                endHistoryStmt.setInt(1, item.getId());
                endHistoryStmt.executeUpdate();

                insertHistoryStmt.setInt(1, item.getId());
                insertHistoryStmt.setString(2, item.getName());
                insertHistoryStmt.setString(3, item.getDescription());
                insertHistoryStmt.setDouble(4, item.getPrice());
                insertHistoryStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void deleteItem(int itemId) throws SQLException {
        String checkExistenceSql = "SELECT 1 FROM Item WHERE id = ?";
        String deleteItemSql = "DELETE FROM Item WHERE id = ?";
        String endHistorySql = "UPDATE Item_History SET t_end = CURRENT_TIMESTAMP WHERE item_id = ? AND t_end IS NULL";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkExistenceSql)) {
                checkStmt.setInt(1, itemId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Item with ID " + itemId + " does not exist.");
                    }
                }
            }

            try (PreparedStatement endHistoryStmt = conn.prepareStatement(endHistorySql);
                 PreparedStatement deleteItemStmt = conn.prepareStatement(deleteItemSql)) {

                // update history
                endHistoryStmt.setInt(1, itemId);
                endHistoryStmt.executeUpdate();

                deleteItemStmt.setInt(1, itemId);
                deleteItemStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
