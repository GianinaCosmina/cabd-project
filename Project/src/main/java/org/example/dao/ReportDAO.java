package org.example.dao;

import org.example.DatabaseManager;
import org.example.model.Item;
import org.example.model.ItemHistoryRecord;
import org.example.model.PeriodReport;
import org.example.model.PriceDifferenceReport;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportDAO {
    public Item getCurrentItemState(int itemId) throws SQLException {
        String sql = "SELECT id, name, description, price FROM Item WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Item(
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price")
                    );
                }
            }
        }
        return null;
    }
    public List<PeriodReport> getLongestPricePeriod() throws SQLException {
        List<PeriodReport> reports = new ArrayList<>();

        // Interogarea SQL pentru a obține durata în intervale
        String sql = "WITH price_periods AS (" +
                "    SELECT item_id, " +
                "           price, " +
                "           t_start, " +
                "           t_end, " +
                "           (t_end - t_start) AS duration " +
                "    FROM item_history " +
                "    WHERE price = (SELECT MIN(price) FROM item_history WHERE item_id = item_history.item_id) " +
                "       OR price = (SELECT MAX(price) FROM item_history WHERE item_id = item_history.item_id) " +
                ") " +
                "SELECT item_id, " +
                "       price, " +
                "       t_start, " +
                "       t_end, " +
                "       duration " +
                "FROM price_periods " +
                "ORDER BY duration ASC ";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                double price = rs.getDouble("price");
                Timestamp startTime = rs.getTimestamp("t_start");
                Timestamp endTime = rs.getTimestamp("t_end");

                // Calculăm durata ca interval
                long durationInMillis = 0;
                String duration = rs.getString("duration"); // Acesta va fi un interval în format 'xx days xx:xx:xx'

                // Extragem durata în milisecunde din interval
                if (duration != null) {
                    String[] parts = duration.split(" ");
                    if (parts.length > 1) {
                        String[] timeParts = parts[1].split(":");
                        long hours = Long.parseLong(timeParts[0]);
                        long minutes = Long.parseLong(timeParts[1]);
                        long seconds = Long.parseLong(timeParts[2]);
                        durationInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;
                    }
                }

                PeriodReport report = new PeriodReport(itemId, price, startTime, endTime, durationInMillis);
                reports.add(report);
            }
        }

        return reports;
    }

    public List<PriceDifferenceReport> getPriceDifferences() throws SQLException {
        String query =
                "SELECT " +
                        "    h.item_id, " +
                        "    h.name, " +
                        "    h.price AS current_price, " +
                        "    LAG(h.price) OVER (PARTITION BY h.item_id ORDER BY h.t_start) AS previous_price, " +
                        "    h.t_start, " +
                        "    h.t_end, " +
                        "    h.price - LAG(h.price) OVER (PARTITION BY h.item_id ORDER BY h.t_start) AS price_difference " +
                        "FROM " +
                        "    item_history h " +
                        "ORDER BY " +
                        "    h.item_id, h.t_start;";

        List<PriceDifferenceReport> reports = new ArrayList<>();
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String name = rs.getString("name");
                BigDecimal currentPrice = BigDecimal.valueOf(rs.getDouble("current_price"));
                BigDecimal previousPrice = rs.getObject("previous_price", BigDecimal.class);
                BigDecimal priceDifference = BigDecimal.valueOf(rs.getDouble("price_difference"));
                Timestamp tStart = rs.getTimestamp("t_start");
                Timestamp tEnd = rs.getTimestamp("t_end");

                reports.add(new PriceDifferenceReport(itemId, name, currentPrice, previousPrice, priceDifference, tStart, tEnd));
            }
        }
        return reports;
    }

    public List<ItemHistoryRecord> getStateAtMoment(Timestamp t) throws SQLException {
        //am luat interval deschis la t_end ca deja nu mai e in acea stare in acel moment
        String sql =
                "SELECT id, name, description, price, t_start, t_end, item_id " +
                        "FROM item_history " +
                        "WHERE t_start <= ? AND (t_end > ? OR t_end IS NULL);";

        List<ItemHistoryRecord> result = new ArrayList<>();
        try (PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql)) {
            statement.setTimestamp(1, t);
            statement.setTimestamp(2, t);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ItemHistoryRecord record = new ItemHistoryRecord(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getBigDecimal("price"),
                            resultSet.getTimestamp("t_start"),
                            resultSet.getTimestamp("t_end"),
                            resultSet.getInt("item_id")
                    );
                    result.add(record);
                }
            }
        }

        return result;
    }


}
