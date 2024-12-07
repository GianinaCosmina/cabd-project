package org.example.dao;

import org.example.DatabaseManager;
import org.example.model.Customer;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDAO {
    public void createCustomer(Customer customer) throws SQLException {
        if (customer.getName() == null || customer.getName().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        }
        if (customer.getAddress() == null || customer.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Customer address cannot be null or empty.");
        }

        String insertCustomerSql = "INSERT INTO Customer (name, address) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement insertCustomerStmt = conn.prepareStatement(insertCustomerSql, Statement.RETURN_GENERATED_KEYS)) {

            insertCustomerStmt.setString(1, customer.getName());
            insertCustomerStmt.setString(2, customer.getAddress());
            insertCustomerStmt.executeUpdate();

            try (ResultSet rs = insertCustomerStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM Customer";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer(rs.getString("name"), rs.getString("address"));
                customer.setId(rs.getInt("id"));
                customers.add(customer);
            }
        }
        return customers;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String checkExistenceSql = "SELECT 1 FROM Customer WHERE id = ?";
        String updateCustomerSql = "UPDATE Customer SET name = ?, address = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkExistenceSql)) {
                checkStmt.setInt(1, customer.getId());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Customer with ID " + customer.getId() + " does not exist.");
                    }
                }
            }

            try (PreparedStatement updateCustomerStmt = conn.prepareStatement(updateCustomerSql)) {
                updateCustomerStmt.setString(1, customer.getName());
                updateCustomerStmt.setString(2, customer.getAddress());
                updateCustomerStmt.setInt(3, customer.getId());
                updateCustomerStmt.executeUpdate();
            }
        }
    }

    public void deleteCustomer(int customerId) throws SQLException {
        String checkExistenceSql = "SELECT 1 FROM Customer WHERE id = ?";
        String deleteCustomerSql = "DELETE FROM Customer WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkExistenceSql)) {
                checkStmt.setInt(1, customerId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Customer with ID " + customerId + " does not exist.");
                    }
                }
            }

            try (PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerSql)) {
                deleteCustomerStmt.setInt(1, customerId);
                deleteCustomerStmt.executeUpdate();
            }
        }
    }
}
