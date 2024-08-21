import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShoppingCart {
    ArrayList<String> items;
    String ownerName;

    // Constructor
    public ShoppingCart(String owner) {
        this.ownerName = owner;
        this.items = new ArrayList<>();
    }

    // Adds a product by barcode to the cart
    public void addProduct(int barcode) {
        File inventoryFile = new File("Stock.txt");
        try (Scanner scanner = new Scanner(inventoryFile)) {
            boolean isProductFound = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] productData = line.split(",");
                if (productData.length < 7) {
                    continue; // Skip malformed lines to avoid runtime errors
                }
                int productBarcode = Integer.parseInt(productData[0].trim());
                int availableQuantity = Integer.parseInt(productData[6].trim());

                if (productBarcode == barcode) {
                    int quantityInCart = (int) items.stream().filter(b -> b.equals(productData[0].trim())).count();
                    if (quantityInCart >= availableQuantity) {
                        System.out.println("Error: Stock limit reached for product with barcode " + barcode);
                        return;
                    }
                    items.add(productData[0]);
                    isProductFound = true;
                }
            }

            if (!isProductFound) {
                System.out.println("Product with barcode " + barcode + " not found.");
            } else {
                System.out.println("Product added to cart: " + barcode);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Inventory file not found.");
        } catch (NumberFormatException e) {
            System.err.println("Error parsing integers from the file.");
        }
    }

    // Displays the contents of the shopping cart
    public void displayContents() {
        System.out.println("Contents of " + ownerName + "'s shopping cart:");
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        HashMap<String, Double> prices = new HashMap<>();
        HashMap<String, String> names = new HashMap<>();
        fetchProductDetails(prices, names);

        HashMap<String, Integer> itemCounts = new HashMap<>();
        items.forEach(item -> itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1));

        double totalCost = 0;
        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            String barcode = entry.getKey();
            String productName = names.getOrDefault(barcode, "Unknown Product");
            int quantity = entry.getValue();
            double price = prices.getOrDefault(barcode, 0.0);
            double cost = price * quantity;
            System.out.println(String.format("%s (%s) x%d - Unit Price: $%.2f, Cost: $%.2f", productName, barcode, quantity, price, cost));
            totalCost += cost;
        }
        System.out.println("Total Cost: $" + totalCost);
    }
    
    public double calculateTotal() {
        try {
            File stockFile = new File("Stock.txt");
            Scanner scanner = new Scanner(stockFile);
            HashMap<Integer, Double> prices = new HashMap<>();
            
            // Read the stock file and store prices of products by barcode
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                int barcode = Integer.parseInt(details[0].trim());
                double price = Double.parseDouble(details[8].trim());  // Assuming price is the 9th element
                prices.put(barcode, price);
            }
            scanner.close();

            // Calculate total price of products in the basket
            double total = 0.0;
            HashMap<Integer, Integer> count = new HashMap<>();
            
            // Count the occurrences of each barcode in the productList
            for (String barcode : items) {
                int bcode = Integer.parseInt(barcode.trim());
                count.put(bcode, count.getOrDefault(bcode, 0) + 1);
            }
            
            // Compute the total cost by multiplying the count of each product by its price
            for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
                int barcode = entry.getKey();
                int quantity = entry.getValue();
                if (prices.containsKey(barcode)) {
                    total += prices.get(barcode) * quantity;
                }
            }

            return total;
        } catch (FileNotFoundException e) {
            System.out.println("Stock file not found.");
            return 0;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number from file.");
            return 0;
        }
    }

    // Fetches product details from the stock file
    private void fetchProductDetails(HashMap<String, Double> prices, HashMap<String, String> names) {
        try (Scanner scanner = new Scanner(new File("Stock.txt"))) {
            while (scanner.hasNextLine()) {
                String[] productData = scanner.nextLine().split(",");
                if (productData.length > 8) {
                    String barcode = productData[0].trim();
                    prices.put(barcode, Double.parseDouble(productData[8].trim()));
                    names.put(barcode, productData[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Inventory file not accessible.");
        }
    }

    // Clears all items from the shopping cart
    public void clearCart() {
        if (!items.isEmpty()) {
            items.clear();
            System.out.println("Shopping cart has been emptied.");
        } else {
            System.out.println("Shopping cart is already empty.");
        }
    }

    public void finalizePurchase() {
        File inventoryFile = new File("Stock.txt");
        File tempFile = new File("Stock_temp.txt");

        try (Scanner scanner = new Scanner(inventoryFile);
             PrintWriter writer = new PrintWriter(tempFile)) {
            HashMap<String, Integer> itemCounts = new HashMap<>();

            items.forEach(item -> itemCounts.merge(item, 1, Integer::sum));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",\\s*"); // This regex split includes spaces after commas if present.
                String barcode = details[0].trim();

                if (itemCounts.containsKey(barcode)) {
                    int currentStock = Integer.parseInt(details[6].trim());
                    int quantityBought = itemCounts.get(barcode);
                    currentStock -= quantityBought;
                    if (currentStock < 0) currentStock = 0; // Prevent negative stock values
                    details[6] = String.valueOf(currentStock);
                }

                // When reconstructing the line, include a space after each comma.
                String joinedDetails = String.join(", ", details); // Adding space after commas
                writer.println(joinedDetails);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Inventory file not found during purchase finalization.");
            return;
        }

        // Attempt to replace the old stock file with the updated one
        if (!inventoryFile.delete()) {
            System.err.println("Failed to delete the original stock file.");
        } else if (!tempFile.renameTo(inventoryFile)) {
            System.err.println("Failed to rename the temporary file to the stock file.");
        } else {
            System.out.println("Inventory updated successfully.");
        }

        items.clear();  // Clear the basket after updating stock
    }



    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart("John Doe");
        cart.addProduct(123456);
        cart.displayContents();
        cart.finalizePurchase();
        
    }
}
