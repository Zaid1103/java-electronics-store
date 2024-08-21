import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Customer extends UserProfile {

    // Constructor initializes Customer details along with User properties
	public Customer(int userID, String username, String name, Address address) {
        // Initialize User properties directly
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.address = address;

    }

    // Retrieve and display peripheral information by barcode
    public void getDetails(String barcode) {
        String stockFilePath = "Stock.txt"; // Define the file path for stock data
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(stockFilePath))) {
            String dataLine;
            boolean isPeripheralFound = false;

            while ((dataLine = bufferedReader.readLine()) != null) {
                String[] itemDetails = dataLine.split(", ");
                if (itemDetails.length > 0 && itemDetails[0].equals(barcode)) {
                    isPeripheralFound = true;
                    String additionalDetail = itemDetails[1].equalsIgnoreCase("mouse") ? 
                                              "Number of buttons: " + itemDetails[9] : 
                                              "Layout: " + itemDetails[9];
                    System.out.println("ID: " + itemDetails[0] +
                                       ", Type: " + itemDetails[1] +
                                       ", Category: " + itemDetails[2] +
                                       ", Brand: " + itemDetails[3] +
                                       ", Color: " + itemDetails[4] +
                                       ", Connection: " + itemDetails[5] +
                                       ", Stock: " + itemDetails[6] +
                                       ", Price: $" + itemDetails[8] +
                                       ", " + additionalDetail);
                }
            }
            if (!isPeripheralFound) {
                System.out.println("No details found for barcode: " + barcode);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    // Filter and display mice based on the number of buttons
    public void filterMiceByButtons(int buttonCount) {
        String stockFilePath = "Stock.txt";
        boolean isMouseFound = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(stockFilePath))) {
            String dataLine;
            while ((dataLine = bufferedReader.readLine()) != null) {
                String[] itemDetails = dataLine.split(", ");
                if ("mouse".equalsIgnoreCase(itemDetails[1].trim()) && Integer.parseInt(itemDetails[9].trim()) == buttonCount) {
                    isMouseFound = true;
                    System.out.println("ID: " + itemDetails[0] + ", Type: " + itemDetails[1] + ", Category: " + itemDetails[2] +
                                       ", Brand: " + itemDetails[3] + ", Color: " + itemDetails[4] + ", Connection: " + itemDetails[5] +
                                       ", Stock: " + itemDetails[6] + ", Price: $" + itemDetails[8] + ", Number of buttons: " + itemDetails[9]);
                }
            }
            if (!isMouseFound) {
                System.out.println("No mouse found with " + buttonCount + " buttons.");
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    // Display all stock items sorted by price
    public void displayAllStock() {
        String stockFilePath = "Stock.txt";
        ArrayList<String[]> stockRecords = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(stockFilePath))) {
            String dataLine;
            while ((dataLine = bufferedReader.readLine()) != null) {
                String[] itemDetails = dataLine.split(", ");
                stockRecords.add(itemDetails);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            return;
        }

        Collections.sort(stockRecords, Comparator.comparingDouble(details -> Double.parseDouble(details[8])));

        for (String[] details : stockRecords) {
            String additionalDetail = details[1].equalsIgnoreCase("mouse") ?
                                      "Number of buttons: " + details[9] :
                                      "Layout: " + details[9];
            System.out.println("ID: " + details[0] + 
                               ", Type: " + details[1] + 
                               ", Category: " + details[2] + 
                               ", Brand: " + details[3] + 
                               ", Color: " + details[4] + 
                               ", Connection: " + details[5] + 
                               ", Stock: " + details[6] + 
                               ", Price: $" + details[8] + 
                               ", " + additionalDetail);
        }
    }
      
}
