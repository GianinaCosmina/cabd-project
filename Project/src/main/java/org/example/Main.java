package org.example;

import org.example.Item;
import org.example.ItemDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ItemDAO itemDAO = new ItemDAO();

        while (true) {
            System.out.println("===== MENU =====");
            System.out.println("1. Add Item");
            System.out.println("2. View All Items");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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
                        itemDAO.deleteItem(id);  // Apelăm metoda de ștergere
                    }

                    case 0 -> {
                        System.out.println("Exiting...");
                        return;
                    }

                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }
}