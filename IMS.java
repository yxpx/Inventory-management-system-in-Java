import java.io.*;
import java.util.Scanner;

public class IMS {
    private static Warehouse warehouse = new Warehouse();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayFileContent("ascii.txt");
        
        while (true) {
            displayMenu();
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    updateItem();
                    break;
                case 4:
                    warehouse.displayItems();
                    break;
                case 5:
                    warehouse.generateBill();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("+-------------------------------+");
        System.out.println("|         Warehouse Menu        |");
        System.out.println("+-------------------------------+");
        System.out.println("| 1. Add Item to Warehouse      |");
        System.out.println("| 2. Remove Item from Warehouse |");
        System.out.println("| 3. Update Warehouse Item      |");
        System.out.println("| 4. Display Warehouse Items    |");
        System.out.println("| 5. Generate Bill              |");
        System.out.println("| 6. Exit                       |");
        System.out.println("+-------------------------------+");
    }

    private static void addItem() {
        System.out.print("Enter item code (3 digits): ");
        int code = scanner.nextInt();
        System.out.print("Enter item name: ");
        String name = scanner.next();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter stock quantity: ");
        int stock = scanner.nextInt();
        System.out.print("Is this a perishable item? (y/n): ");
        String isPerishable = scanner.next();
        
        if (isPerishable.equalsIgnoreCase("y")) {
            System.out.print("Enter expiration date (YYYY-MM-DD): ");
            String expirationDate = scanner.next();
            warehouse.addItem(code, name, price, stock, expirationDate);
        } else {
            warehouse.addItem(code, name, price, stock, null);
        }
    }

    private static void removeItem() {
        System.out.print("Enter item code to remove: ");
        int code = scanner.nextInt();
        warehouse.removeItem(code);
    }

    private static void updateItem() {
        System.out.print("Enter item code to update: ");
        int code = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new item price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new stock quantity: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Is this a perishable item? (y/n): ");
        String isPerishable = scanner.nextLine();
        
        String expirationDate = null;
        if (isPerishable.equalsIgnoreCase("y")) {
            System.out.print("Enter new expiration date (YYYY-MM-DD): ");
            expirationDate = scanner.nextLine();
        }
        
        warehouse.updateItem(code, name, price, stock, expirationDate);
    }

    private static void displayFileContent(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
