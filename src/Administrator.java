// Administrator.java
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Administrator extends UserProfile {

	public Administrator(int userID, String username, String name, Address address) {
        // Directly initialise User properties
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.address = address;

        // Admin-specific initialisation can be added here
    }
    

    public void getInfo(String barcode) {
        // Define the path to the stock file.
        String filePath = "C:\\Users\\Zaid\\eclipse-workspace\\F311024\\Stock.txt";

        // Use a try-with-resources statement to ensure the BufferedReader is closed after use.
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isItemFound = false;  // Flag to track if the item is found.

            // Read the file line by line until the end of the file.
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");  // Split each line into parts using a comma as the delimiter.

                // Check if the first element matches the barcode.
                if (details.length > 0 && details[0].equals(barcode)) {
                    isItemFound = true;  // Set the flag to true if the item is found.
                    
                    // Determine the additional label based on the type of peripheral.
                    String additionalDetail = "Layout: " + details[9];
                    if (details[1].equalsIgnoreCase("mouse")) {
                        additionalDetail = "Number of buttons: " + details[9];
                    }

                    // Output the details of the peripheral.
                    System.out.println(String.format("ID: %s, Type: %s, Category: %s, Brand: %s, Color: %s, Connection: %s, Stock: %s, Original Price: $%s, Retail Price: $%s, %s",
                                        details[0], details[1], details[2], details[3], details[4], details[5], details[6], details[7], details[8], additionalDetail));
                    break;  // Exit the loop after finding and displaying the item.
                }
            }

            // If no item matches, inform the user.
            if (!isItemFound) {
                System.out.println("Item not found.");
            }
        } catch (IOException e) {
            // Handle potential IOExceptions from file operations.
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
    
    public void addPeripheral(ProductInfo product) {
        String filePath = "Stock.txt"; // Ensure this is the correct path on your system
        String data = String.format("%d, %s, %s, %s, %s, %s, %d, %.2f, %.2f, %s",
                                    product.getBarcode(),
                                    product.getCategory().toString().toLowerCase(),
                                    product.getType().toString().toLowerCase(),
                                    product.getBrand(),
                                    product.getColor(),
                                    product.getConnectivity().toString().toLowerCase(), 
                                    product.getQuantityInStock(),
                                    product.getOriginalCost(),
                                    product.getRetailPrice(),
                                    product.getLayout().toString());
        
        File file = new File(filePath);
        if (itemExists(product.getBarcode(), filePath)) {
            System.out.println("Error: A peripheral with barcode " + product.getBarcode() + " already exists in the stock file.");
            return; // Exit if a matching barcode is found
        }

        // Append new entry to the file
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(data);
            System.out.println("Peripheral added successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private boolean itemExists(int barcode, String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith(barcode + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading the file: " + e.getMessage());
        }
        return false;
    }




    public void displayStock() {
        String filePath = "C:\\Users\\Zaid\\eclipse-workspace\\F311024\\Stock.txt";
        List<String[]> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            int lineCount = 0; // Add a line counter to help identify the problematic line
            while ((currentLine = reader.readLine()) != null) {
                lineCount++;
                String[] parsedDetails = currentLine.split(",\\s*");
                if (parsedDetails.length < 10) {
                    System.err.println("Skipping malformed line at " + lineCount + ": " + currentLine); // Log line number and content
                    continue;
                }
                records.add(parsedDetails);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        try {
            records.sort(Comparator.comparingDouble(details -> Double.parseDouble(details[8])));
        } catch (NumberFormatException e) {
            System.err.println("Number format error during sorting: " + e.getMessage());
        }

        for (String[] details : records) {
            if (details.length < 10) {
                continue; // Additional safeguard
            }
            String additionalDetail = "mouse".equalsIgnoreCase(details[1]) ? "Number of buttons: " + details[9] : "Layout: " + details[9];
            System.out.printf("ID: %s, Type: %s, Category: %s, Brand: %s, Color: %s, Connection: %s, Stock: %s, Original Price: $%s, Retail Price: $%s, %s%n",
                              details[0], details[1], details[2], details[3], details[4], details[5], details[6], details[7], details[8], additionalDetail);
        }
    }


   
    
}