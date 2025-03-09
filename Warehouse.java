import java.util.*;

public class Warehouse {
    private static final int HASH_TABLE_SIZE = 10;
    private List<Product>[] warehouse;

    public Warehouse() {
        warehouse = new ArrayList[HASH_TABLE_SIZE];
        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            warehouse[i] = new ArrayList<>();
        }
    }

    private int hashFunction(int code) {
        return code % HASH_TABLE_SIZE;
    }

    public void addItem(int code, String name, double price, int stock, String expirationDate) {
        int index = hashFunction(code);
        Product newProduct;
        if (expirationDate != null) {
            newProduct = new PerishableItem(code, name, price, stock, expirationDate);
        } else {
            newProduct = new Item(code, name, price, stock);
        }
        warehouse[index].add(newProduct);
        System.out.println("Product added to warehouse: " + newProduct.getName());
        System.out.println();
    }

    public void removeItem(int code) {
        int index = hashFunction(code);
        for (Iterator<Product> iterator = warehouse[index].iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if (product.getCode() == code) {
                iterator.remove();
                System.out.println("Product removed from warehouse: " + product.getName());
                System.out.println();
                return;
            }
        }
        System.out.println("Product not found in warehouse.");
        System.out.println();
    }

    public void updateItem(int code, String name, double price, int stock, String expirationDate) {
        int index = hashFunction(code);
        for (Product product : warehouse[index]) {
            if (product.getCode() == code) {
                product.setName(name);
                product.setPrice(price);
                product.setStock(stock);
                
                if (product instanceof PerishableItem) {
                    ((PerishableItem) product).setExpirationDate(expirationDate);
                    System.out.println("Warehouse perishable product updated: " + name + " (Expiry: " + expirationDate + ")");
                } else {
                    System.out.println("Warehouse product updated: " + name);
                }
                System.out.println();
                return;
            }
        }
        System.out.println("Product not found in warehouse.");
        System.out.println();
    }

    public void displayItems() {
        System.out.println("\n+-----------------+------------------+----------+-------+------------------+");
        System.out.println("| Code            | Name             | Price    | Stock | Expiry Date      |");
        System.out.println("+-----------------+------------------+----------+-------+------------------+");
        
        for (List<Product> bucket : warehouse) {
            for (Product product : bucket) {
                System.out.printf("| %-15d | %-16s | %-8.2f | %-5d | %-16s |\n", 
                    product.getCode(), product.getName(), product.getPrice(), product.getStock(), product.getDescription());
            }
        }

        System.out.println("+-----------------+------------------+----------+-------+------------------+");
        System.out.println();
    }

    public void generateBill() {
        Scanner scanner = new Scanner(System.in);
        double totalAmount = 0.0;
        List<BillItem> billItems = new ArrayList<>();

        System.out.println("\nEnter the item code (3 digits) and quantity for each item (Enter -1 to finish):");

        while (true) {
            System.out.print("Item code: ");
            int code = scanner.nextInt();
            if (code == -1) break;

            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();

            int index = hashFunction(code);
            Product product = findProduct(code, index);

            if (product == null) {
                System.out.println("Product not found in warehouse.");
            } else if (quantity > product.getStock()) {
                System.out.println("Insufficient stock for product: " + product.getName());
            } else {
                double amount = product.getPrice() * quantity;
                billItems.add(new BillItem(product.getCode(), product.getName(), quantity, amount));
                totalAmount += amount;
                product.setStock(product.getStock() - quantity);
            }
        }

        printBill(billItems, totalAmount);
    }

    private Product findProduct(int code, int index) {
        for (Product product : warehouse[index]) {
            if (product.getCode() == code) {
                return product;
            }
        }
        return null;
    }

    private void printBill(List<BillItem> billItems, double totalAmount) {
        System.out.println("+-----------------+------------------+----------+---------------+");
        System.out.println("| Code            | Name             | Quantity | Amount        |");
        System.out.println("+-----------------+------------------+----------+---------------+");
        for (BillItem item : billItems) {
            System.out.printf("| %-15d | %-16s | %-8d | %-13.2f |\n", 
                item.code, item.name, item.quantity, item.amount);
        }
        System.out.println("+-----------------+------------------+----------+---------------+");
        System.out.printf("| Total Amount: %-48.2f|\n", totalAmount);
        System.out.println("+---------------------------------------------------------------+");
        System.out.println();
    }

    private static class BillItem {
        int code;
        String name;
        int quantity;
        double amount;

        BillItem(int code, String name, int quantity, double amount) {
            this.code = code;
            this.name = name;
            this.quantity = quantity;
            this.amount = amount;
        }
    }
}
