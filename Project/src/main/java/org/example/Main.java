package org.example;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ItemDAO itemDAO = new ItemDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        OrderDAO orderDAO = new OrderDAO();
        ReportDAO reportDAO = new ReportDAO();

        while (true) {
            System.out.println("===== MAIN MENU =====");
            System.out.println("1. Manage Items");
            System.out.println("2. Manage Customers");
            System.out.println("3. Manage Orders");
            System.out.println("4. Manage Reports");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1 -> manageItems(scanner, itemDAO);
                case 2 -> manageCustomers(scanner, customerDAO);
                case 3 -> manageOrders(scanner, orderDAO);
                case 4 -> manageReports(scanner, reportDAO);
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void manageItems(Scanner scanner, ItemDAO itemDAO) {
        while (true) {
            System.out.println("\n===== ITEM MENU =====");
            System.out.println("1. Add Item");
            System.out.println("2. View All Items");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter item name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter item description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter item price: ");
                        double price = scanner.nextDouble();

                        Item item = new Item(name, description, price);
                        itemDAO.createItem(item);
                        System.out.println("Item added successfully.");
                    }
                    case 2 -> {
                        for (Item item : itemDAO.getAllItems()) {
                            System.out.println(item);
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter item ID to update: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter new description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter new price: ");
                        double price = scanner.nextDouble();

                        Item item = new Item(name, description, price);
                        item.setId(id);
                        itemDAO.updateItem(item);
                        System.out.println("Item updated successfully.");
                    }
                    case 4 -> {
                        System.out.print("Enter item ID to delete: ");
                        int id = scanner.nextInt();
                        itemDAO.deleteItem(id);
                        System.out.println("Item deleted successfully.");
                    }
                    case 0 -> {
                        System.out.println("Returning to main menu...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }

    private static void manageReports(Scanner scanner, ReportDAO reportDAO) {
        while (true) {
            System.out.println("\n===== REPORT MENU =====");
            System.out.println("1. View current item state");
            System.out.println("2. View longest period for item price min or max");
            System.out.println("4. View time differences for item price");
            System.out.println("4. View state at a specific moment");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter item ID: ");
                        int itemId = scanner.nextInt();
                        Item item = reportDAO.getCurrentItemState(itemId);
                        System.out.println(item != null ? "Current Item: " + item : "Item not found.");
                    }
                    case 2 -> getLongestPricePeriod(reportDAO);
                    case 3 -> generatePriceDifferenceReport(reportDAO);
                    case 4 -> viewStateAtMoment(scanner, reportDAO);

                    case 0 -> {
                        System.out.println("Returning to main menu...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }

    private static void manageCustomers(Scanner scanner, CustomerDAO customerDAO) {
        while (true) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter customer name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter customer address: ");
                        String address = scanner.nextLine();

                        Customer customer = new Customer(name, address);
                        customerDAO.createCustomer(customer);
                        System.out.println("Customer added successfully.");
                    }
                    case 2 -> {
                        for (Customer customer : customerDAO.getAllCustomers()) {
                            System.out.println(customer);
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter customer ID to update: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter new address: ");
                        String address = scanner.nextLine();

                        Customer customer = new Customer(name, address);
                        customer.setId(id);
                        customerDAO.updateCustomer(customer);
                        System.out.println("Customer updated successfully.");
                    }
                    case 4 -> {
                        System.out.print("Enter customer ID to delete: ");
                        int id = scanner.nextInt();
                        customerDAO.deleteCustomer(id);
                        System.out.println("Customer deleted successfully.");
                    }
                    case 0 -> {
                        System.out.println("Returning to main menu...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }

    private static void manageOrders(Scanner scanner, OrderDAO orderDAO) {
        while (true) {
            System.out.println("\n===== ORDER MENU =====");
            System.out.println("1. Add Order");
            System.out.println("2. View All Orders");
            System.out.println("3. Update Order");
            System.out.println("4. Delete Order Item");
            System.out.println("5. Delete Entire Order");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter order ID: ");
                        int orderId = scanner.nextInt();
                        System.out.print("Enter customer ID: ");
                        int customerId = scanner.nextInt();
                        System.out.print("Enter item ID: ");
                        int itemId = scanner.nextInt();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter comments: ");
                        String comments = scanner.nextLine();

                        Order order = new Order(orderId, customerId, itemId, quantity, comments);
                        orderDAO.createOrder(order);
                        System.out.println("Order added successfully.");
                    }
                    case 2 -> {
                        for (Order order : orderDAO.getAllOrders()) {
                            System.out.println(order);
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter order ID to update: ");
                        int orderId = scanner.nextInt();
                        System.out.print("Enter customer ID: ");
                        int customerId = scanner.nextInt();
                        System.out.print("Enter item ID to update: ");
                        int itemId = scanner.nextInt();
                        System.out.print("Enter new quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new comments: ");
                        String comments = scanner.nextLine();

                        Order order = new Order(orderId, customerId, itemId, quantity, comments);
                        orderDAO.updateOrder(order);
                        System.out.println("Order updated successfully.");
                    }
                    case 4 -> {
                        System.out.print("Enter order ID to delete item from: ");
                        int orderId = scanner.nextInt();
                        System.out.print("Enter item ID to delete: ");
                        int itemId = scanner.nextInt();

                        orderDAO.deleteOrderItem(orderId, itemId);
                        System.out.println("Order item deleted successfully.");
                    }
                    case 5 -> {
                        System.out.print("Enter order ID to delete: ");
                        int orderId = scanner.nextInt();

                        System.out.println("Are you sure you want to delete the entire order with ID " + orderId + "?");
                        System.out.print("Enter 'yes' to confirm: ");
                        String confirmation = scanner.next();

                        if (confirmation.equalsIgnoreCase("yes")) {
                            orderDAO.deleteOrder(orderId);
                            System.out.println("Entire order deleted successfully.");
                        } else {
                            System.out.println("Order deletion canceled.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Returning to main menu...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }

    private static void getLongestPricePeriod(ReportDAO reportDAO) throws SQLException {
        List<PeriodReport> reports = reportDAO.getLongestPricePeriod();
        for (PeriodReport report : reports) {
            System.out.println(report);
        }
    }

    private static void generatePriceDifferenceReport(ReportDAO reportDAO) {
        System.out.println("\n===== PRICE DIFFERENCE REPORT =====");
        try {
            List<PriceDifferenceReport> reports = reportDAO.getPriceDifferences();
            for (PriceDifferenceReport report : reports) {
                System.out.println(report);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void viewStateAtMoment(Scanner scanner, ReportDAO reportDAO) {
        System.out.print("Enter the moment (YYYY-MM-DD HH:MM:SS): ");
        String input = scanner.nextLine();

        try {
            Timestamp t = Timestamp.valueOf(input);
            List<ItemHistoryRecord> records = reportDAO.getStateAtMoment(t);

            if (records.isEmpty()) {
                System.out.println("No records found for the given timestamp.");
            } else {
                System.out.println("State at " + t + ":");
                for (ItemHistoryRecord record : records) {
                    System.out.println(record);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid timestamp format. Please try again.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

}
