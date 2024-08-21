import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminMenu {
    private static Administrator admin;
    private static int barcode;
	private static String brand;
	private static ProductCategory productCategory;
	private static DeviceType deviceType;
	private static String color;
	private static ConnectivityType connectivity;
	private static Layout layout;
	private static double originalCost;
	private static int quantityinStock;
	private static double retailPrice;

    public AdminMenu(Administrator admin) {
        this.admin = admin;

    }

    public static void adminMainMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Choose an option (input the number):");
                System.out.println("1 : Display stock");
                System.out.println("2 : Add new product");
                System.out.println("3 : Exit");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            admin.displayStock(); // Assuming 'admin' has a method 'displayStock'
                            stockMenuPost(); // Assuming there is a follow-up menu after displaying stock
                            return; // Return to ensure clean exit after action
                        case 2:
                            menuAddBarcode(); // Assuming there's a method to handle adding a new barcode
                            return; // Return after adding a product
                        case 3:
                            System.out.println("Exiting");
                            System.exit(0); // Terminate the application
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 3.");
                            break; // Continue the loop for another input
                    }
                } else {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.next(); // Clear the invalid input before the next prompt
                }
            }
        } // Scanner automatically closes here
    }

    public static void stockMenuPost() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Enter 0 to go back to the main menu");
                System.out.print("Enter your choice: ");

                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();
                    switch (userInput) {
                        case 0:
                            adminMainMenu();  // Navigate back to the admin main menu
                            return; // Exit the method completely after navigation
                        default:
                            System.out.println("Invalid input. Enter '0' to return to the main menu.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();  // Clear the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }
    
    private static HashSet<Integer> loadExistingBarcodes() {
        HashSet<Integer> existingBarcodes = new HashSet<>();
        File stockFile = new File("Stock.txt");

        // Using try-with-resources to ensure the scanner is closed after use
        try (Scanner fileScanner = new Scanner(stockFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] details = line.split(",");
                try {
                    int barcode = Integer.parseInt(details[0].trim()); // Convert the first value in each row to integer
                    existingBarcodes.add(barcode);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing barcode from line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Stock file not found.");
        }
        return existingBarcodes;
    }

    public static void menuAddBarcode() {
        HashSet<Integer> existingBarcodes = loadExistingBarcodes(); // Load existing barcodes once

        // Using try-with-resources to ensure the scanner is closed after use
        try (Scanner scanner = new Scanner(System.in)) {
            String userInput;

            while (true) {
                System.out.println();
                System.out.println("Input a 6-digit barcode (or 0 to go back):");

                userInput = scanner.nextLine(); // Read the input as a string to preserve leading zeros

                switch (userInput) {
                    case "0":
                        adminMainMenu();  // Navigate back to the admin main menu
                        return; // Exit the method completely after navigation
                    default:
                        if (userInput.matches("\\d{6}")) {  // Validate the barcode format
                            int userNumber = Integer.parseInt(userInput);
                            if (existingBarcodes.contains(userNumber)) {
                                System.out.println("Error: This barcode already exists. Enter a new barcode.");
                            } else {
                                barcode = userNumber; // Assign the barcode after validation
                                addCategoryMenu(); // Proceed to category adding
                                return; // Exit after successful addition
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a 6-digit barcode.");
                        }
                        break;
                }
            }
        } // No need to manually close the scanner here due to try-with-resources
    }
    
    public static void addCategoryMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Choose a device type:");
                System.out.println("1 : Keyboard");
                System.out.println("2 : Mouse");
                System.out.println("3 : Back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            productCategory = ProductCategory.valueOf("KEYBOARD");
                            keyboardTypeMenu();  // Navigate to the keyboard type menu
                            return; // Exit the method after the action
                        case 2:
                            productCategory = ProductCategory.valueOf("MOUSE");
                            mouseTypeMenu();  // Navigate to the mouse type menu
                            return; // Exit the method after the action
                        case 3:
                            menuAddBarcode();  // Navigate back to the barcode menu
                            return; // Exit the method after the action
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 3.");
                            break; // Continue the loop for another input
                    }
                } else {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.next();  // Clear the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }

    public static void keyboardTypeMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Choose a keyboard type:");
                System.out.println("1 : Standard");
                System.out.println("2 : Gaming");
                System.out.println("3 : Flexible");
                System.out.println("4 : Back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                        	deviceType = DeviceType.valueOf("STANDARD");
                            addBrandMenu();  // Navigate to the brand menu
                            return; // Exit the method after the action
                        case 2:
                        	deviceType = DeviceType.valueOf("GAMING");
                            addBrandMenu();  // Navigate to the brand menu
                            return; // Exit the method after the action
                        case 3:
                        	deviceType = DeviceType.valueOf("FLEXIBLE");
                            addBrandMenu();  // Navigate to the brand menu
                            return; // Exit the method after the action
                        case 4:
                            addCategoryMenu();  // Navigate back to the category menu
                            return; // Exit the method after the action
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 4.");
                            break; // Continue the loop for another input
                    }
                } else {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.next();  // Clear the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }

    public static void mouseTypeMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Choose a mouse type:");
                System.out.println("1 : Standard");
                System.out.println("2 : Gaming");
                System.out.println("3 : Back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            deviceType = DeviceType.valueOf("STANDARD"); // Assign the type of mouse
                            addBrandMenu();  // Navigate to the brand menu
                            return; // Exit the method after the action
                        case 2:
                        	deviceType = DeviceType.valueOf("GAMING"); // Assign the type of mouse
                            addBrandMenu();  // Navigate to the brand menu
                            return; // Exit the method after the action
                        case 3:
                            addCategoryMenu();  // Navigate back to the category menu
                            return; // Exit the method after the action
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 3.");
                            break; // Continue the loop for another input
                    }
                } else {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.next();  // Clear the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }

    public static void addBrandMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            String userInput;

            while (true) {
                System.out.println();
                System.out.println("Input brand (or 0 to go back):");

                userInput = scanner.nextLine(); // Read the input as a string to handle input properly

                if (userInput.equals("0")) {
                    navigateToPreviousMenu(); // A helper method to handle navigation based on the product category
                    return; // Exit the method after navigation
                } else if (userInput.length() <= 20) { // Ensure the input is no more than 20 characters
                    brand = userInput;
                    addColourMenu(); // Proceed to add colour menu
                    return; // Exit the method after action
                } else {
                    System.out.println("Input should be no more than 20 characters.");
                }
            }
        } // Scanner is automatically closed here due to try-with-resources
    }

    private static void navigateToPreviousMenu() {
    	if (productCategory == ProductCategory.KEYBOARD) {
            keyboardLayoutMenu();
        } else if (productCategory == ProductCategory.MOUSE)  {
            mouseButtonMenu();
        }
    }
    
    public static void addColourMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            String userInput;

            while (true) {
                System.out.println();
                System.out.println("Input colour (or 0 to go back):");

                userInput = scanner.nextLine(); // Read the input as a string to handle input properly

                if (userInput.equals("0")) {
                    addBrandMenu(); // Navigate back to the brand menu
                    return; // Exit the method after navigation
                } else if (userInput.length() <= 20) { // Ensure the input is no more than 20 characters
                    color = userInput;
                    addConnectivityMenu(); // Proceed to add connectivity menu
                    return; // Exit the method after action
                } else {
                    System.out.println("Input should be no more than 20 characters.");
                }
            }
        } // Scanner is automatically closed here due to try-with-resources
    }

    public static void addConnectivityMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Choose a connectivity option:");
                System.out.println("1 : Wired");
                System.out.println("2 : Wireless");
                System.out.println("3 : Back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            connectivity = ConnectivityType.valueOf("WIRED");
                            addStockMenu();  // Proceed to the stock menu
                            return; // Exit the method after setting the connectivity
                        case 2:
                            connectivity = ConnectivityType.valueOf("WIRELESS");
                            addStockMenu();  // Proceed to the stock menu
                            return; // Exit the method after setting the connectivity
                        case 3:
                            addColourMenu();  // Navigate back to the colour menu
                            return; // Exit the method after navigation
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 3.");
                            break; // Continue the loop for another input
                    }
                } else {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.next();  // Clear the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }

    public static void addStockMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Input quantity stocked or 0 to go back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    if (userInput == 0) {
                        addConnectivityMenu();  // Navigate back to the connectivity menu
                        return; // Exit the method completely after navigation
                    } else if (userInput > 0) {
                        quantityinStock = userInput; // Save the valid quantity
                        originalCostMenu(); // Proceed to the original cost menu
                        return; // Exit the method after setting the stock quantity
                    } else {
                        System.out.println("Invalid input, please enter a positive integer or 0 to go back.");
                    }
                } else {
                    System.out.println("Invalid input, please enter an integer.");
                    scanner.next();  // Clear the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }

    public static void originalCostMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            double userInput;

            while (true) {
                System.out.println();
                System.out.println("Input original cost (decimal values allowed) or 0 to go back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextDouble()) {
                    userInput = scanner.nextDouble();

                    if (userInput == 0) {
                        addStockMenu();  // Navigate back to the stock menu
                        return; // Exit the method completely after navigation
                    } else if (userInput > 0) {
                        originalCost = userInput; // Set the original cost
                        retailPriceMenu(); // Navigate to the retail price menu
                        return; // Exit the method after setting the cost
                    } else {
                        System.out.println("Invalid input, please enter a positive decimal number or 0 to exit.");
                    }
                } else {
                    System.out.println("Invalid input, please enter a valid decimal number.");
                    scanner.next();  // Clear the non-double input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }

    public static void retailPriceMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            double userInput;

            while (true) {
                System.out.println();
                System.out.println("Input retail price (decimal values allowed) or 0 to go back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextDouble()) {
                    userInput = scanner.nextDouble();

                    if (userInput == 0) {
                        originalCostMenu();  // Navigate back to the original cost menu
                        return; // Exit the method completely after navigation
                    } else if (userInput > 0) {
                        retailPrice = userInput; // Set the retail price
                        navigateToProductSpecificMenu(); // Proceed to product-specific menu based on category
                        return; // Exit the method after setting the price
                    } else {
                        System.out.println("Invalid input, please enter a positive decimal number or 0 to exit.");
                    }
                } else {
                    System.out.println("Invalid input, please enter a valid decimal number.");
                    scanner.next();  // Clear the non-double input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }

    private static void navigateToProductSpecificMenu() {
        if (productCategory == ProductCategory.KEYBOARD) {
            keyboardLayoutMenu();
        } else if (productCategory == ProductCategory.MOUSE)  {
            mouseButtonMenu();
        }
    }

    public static void keyboardLayoutMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Choose a keyboard layout:");
                System.out.println("1 : UK");
                System.out.println("2 : US");
                System.out.println("3 : Back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                        	layout = Layout.valueOf("UK"); // Set the extra detail for the keyboard layout
                            ProductInfo product = new ProductInfo( barcode, brand,  color, connectivity, quantityinStock, originalCost, retailPrice, productCategory, layout, deviceType);
                            admin.addPeripheral(product);
                            adminMainMenu(); // Navigate back to the admin main menu
                            return; // Exit the method completely after the action
                        case 2:
                        	layout = Layout.valueOf("US");; // Set the extra detail for the keyboard layout
                            ProductInfo product2 = new ProductInfo( barcode, brand,  color, connectivity, quantityinStock, originalCost, retailPrice, productCategory, layout, deviceType);
                            admin.addPeripheral(product2);
                            adminMainMenu(); // Navigate back to the admin main menu
                            return; // Exit the method completely after the action
                        case 3:
                            retailPriceMenu(); // Navigate back to the retail price menu
                            return; // Exit the method completely after the action
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 3.");
                            break; // Continue the loop for another input
                    }
                } else {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.next();  // Clear the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }
    
    public static void mouseButtonMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Input number of buttons (or 0 to go back):");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    if (userInput == 0) {
                        retailPriceMenu();  // Navigate back to the retail price menu
                        return; // Exit the method after navigation
                    } else if (userInput > 0) {
                        ProductInfo product = new ProductInfo( barcode, brand,  color, connectivity, quantityinStock, originalCost, retailPrice, productCategory, layout, deviceType);
                        admin.addPeripheral(product);
                        adminMainMenu(); // Navigate back to the admin main menu
                        return; // Exit the method after adding the peripheral
                    } else {
                        System.out.println("Invalid input, please enter a positive integer or 0.");
                    }
                } else {
                    System.out.println("Invalid input, please enter a positive integer or 0.");
                    scanner.next();  // Clear the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }
    
    
}