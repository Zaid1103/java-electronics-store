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

public class UserMenu {
    private  static ShoppingCart cart;
    private  static Customer customer;
    private static String cardNumber;
	private static String email;
	private static String securityCode;

    public UserMenu(Customer customer) {
        this.customer = customer;
        this.cart = new ShoppingCart(customer.name);
    }

    public static void userMainMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println("\nWhat would you like to do? (input the number):");
                System.out.println("1 : Search for items");
                System.out.println("2 : View cart contents");
                System.out.println("3 : Exit");

                System.out.print("Enter choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            searchMenuUser(); // Navigate to search menu
                            break;
                        case 2:
                            userBasketMenu(); // Navigate to basket view
                            break;
                        case 3:
                            System.out.println("Exiting");
                            return; // Use return to exit the method instead of System.exit(0) for cleaner exit and potential unit testing
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 3.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.next();  // Consume the non-integer input
                }
            }
        }
    }

    public static void searchMenuUser() {
        Scanner scanner = new Scanner(System.in);
        try {
            int userInput;
            boolean exit = false;

            while (!exit) {
                System.out.println("\nChoose from below:");
                System.out.println("1 : View all items");
                System.out.println("2 : Search for product by barcode");
                System.out.println("3 : Search by number of mouse buttons");
                System.out.println("4 : Add an item to basket");
                System.out.println("5 : Back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();
                    switch (userInput) {
                        case 1:
                            customer.displayAllStock();
                            displayMenuPost();
                            exit = true;
                            break;
                        case 2:
                            searchMenuBarcode();
                            exit = true;
                            break;
                        case 3:
                            buttonFilterMenu();
                            exit = true;
                            break;
                        case 4:
                            addProductToBasketMenu();
                            exit = true;
                            break;
                        case 5:
                            userMainMenu();
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 5.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.next();  // Consume the non-integer input
                }
            }
        } finally {
            scanner.close();  // Ensure scanner is closed to avoid resource leak
        }
    }
    
    public static void displayMenuPost() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                System.out.println("Enter 0 to go back to the search menu");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    int userInput = scanner.nextInt();
                    if (userInput == 0) {
                        searchMenuUser();  // Navigate back to the search menu
                        break;  // Break the loop after successfully handling the user's choice
                    } else {
                        System.out.println("Invalid input. Please enter 0 to go back.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();  // Clear the non-integer input from the scanner buffer
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }
    
    public static void searchMenuBarcode() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                System.out.println("Enter barcode of product you wish to see (or input '0' to go back):");

                // Ensure the prompt clearly requests an integer input
                System.out.print("Enter barcode or 0 to return: ");
                if (scanner.hasNextInt()) {
                    int userInput = scanner.nextInt();
                    if (userInput == 0) {
                        searchMenuUser(); // Return to the search menu
                        break; // Exit the loop after handling return
                    } else {
                        // Process the barcode as a string for further use
                        String barcodeString = String.valueOf(userInput);
                        customer.getDetails(barcodeString);
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid barcode or '0' to go back.");
                    scanner.next(); // Consume the invalid input to prevent an infinite loop
                }
            }
        } // Scanner is automatically closed here
    }

    public static void buttonFilterMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Enter the number of buttons on the mouse that you want to see (or input '0' to go back):");
                System.out.print("Enter a number or 0 to return: ");

                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();
                    if (userInput == 0) {
                        searchMenuUser(); // Go back to the previous menu
                        break; // Exit the loop after handling the input
                    } else {
                        // Assuming `mouseButtonFilter` is a valid method for filtering mice by the number of buttons
                        customer.filterMiceByButtons(userInput);
                        // After displaying filtered results, you may want to allow another search or go back
                        System.out.println("Enter another number or '0' to go back.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Consume the invalid input to avoid an infinite loop
                }
            }
        } // The scanner will automatically close here, thanks to try-with-resources
    }

    public static void addProductToBasketMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Enter the barcode of the item you wish to add to the cart (or input '0' to go back):");
                System.out.print("Enter barcode or 0 to return: ");

                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();
                    if (userInput == 0) {
                        searchMenuUser(); // Return to the search menu
                        break; // Exit the loop after handling the input
                    } else {
                        // Assuming `addToBasket` is a valid method in the `basket` object
                        // and `basket` is correctly instantiated and accessible here
                        cart.addProduct(userInput);
                        // Provide feedback that the product was added, and allow for more additions
                        System.out.println("Product added. Enter another barcode or '0' to go back.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Clear the non-integer input from the scanner buffer
                }
            }
        } // The scanner will automatically close here, thanks to try-with-resources
    }

    public static void userBasketMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                cart.displayContents();
                System.out.println("\nEnter the number of what you wish to do with your items in the cart:");
                System.out.println("1 : Clear Cart");
                System.out.println("2 : Proceed to payment");
                System.out.println("3 : Back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            cart.clearCart();
                            System.out.println("Basket cleared.");
                            break;
                        case 2:
                            if (cart.items.isEmpty()) {
                                System.out.println("Error, basket empty. Add items before proceeding to payment.");
                            } else {
                                proceedToPayment();
                                return; // Exit the method after processing payment
                            }
                            break;
                        case 3:
                            userMainMenu(); // Navigate back to the main menu
                            return; // Exit the method completely
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 3.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input, please enter a valid number.");
                    scanner.next(); // Consume the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner will automatically close here due to try-with-resources
    }
    
    public static void proceedToPayment() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Choose payment form:");
                System.out.println("1 : Credit card payment");
                System.out.println("2 : PayPal payment");
                System.out.println("3 : Back");

                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            menuCardNumber();  // Navigate to the card number input menu
                            return;  // Exit the method after the menu call
                        case 2:
                            menuEmail();  // Navigate to the PayPal email input menu
                            return;  // Exit the method after the menu call
                        case 3:
                            userBasketMenu();  // Navigate back to the basket menu
                            return;  // Exit the method completely
                        default:
                            System.out.println("Invalid input, please choose a number between 1 and 3.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input, please enter a valid number.");
                    scanner.next(); // Consume the non-integer input to prevent an infinite loop
                }
            }
        } // The scanner will automatically close here due to try-with-resources
    }

    public static void menuCardNumber() {
        try (Scanner scanner = new Scanner(System.in)) {
            String userInput;

            while (true) {
                System.out.println();
                System.out.println("Enter 6-digit card number (or 0 to go back):");

                userInput = scanner.nextLine(); // Read the input as a string to preserve leading zeros

                if (userInput.equals("0")) {
                    proceedToPayment();
                    break;
                } else if (userInput.matches("\\d{6}")) { // Check if the input is exactly 6 digits
                    cardNumber = userInput;
                    menuSecurityCode();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a 6-digit card number.");
                }
            }
        } // Scanner is closed here, which is generally safe since we're using a new Scanner instance
    }

    public static void menuSecurityCode() {
        // Create a new scanner instance
        Scanner scanner = new Scanner(System.in);

        String userInput;
        while (true) {
            System.out.println();
            System.out.println("Enter 3-digit security code (or 0 to go back):");
            userInput = scanner.nextLine();  // Read the input as a string to preserve leading zeros

            if (userInput.equals("0")) {
                proceedToPayment();
                break;
            } else if (userInput.matches("\\d{3}")) {  // Check if the input is exactly 3 digits
                securityCode = userInput;
                LocalDate today = LocalDate.now();
                // Assuming a correct constructor for CreditCardPayment
                CreditcardPayment payment = new CreditcardPayment(today, cardNumber, securityCode);
                Receipt receipt = payment.processPayment(cart.calculateTotal(), customer.getAddress());  // Process the payment and print the receipt
                receipt.printReceipt();
                cart.finalizePurchase();  // Assume this method handles post-payment logic
                menuPostPayment();  // Navigate to the post-payment menu
                break;
            } else {
                System.out.println("Invalid input. Please enter a 3-digit security code.");
            }
        }

        // Careful with closing System.in wrapped scanners if re-used
        scanner.close();  // Close the scanner only if no further System.in use is expected after this point
    }
    
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false; // Directly return false if email is null
        }
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern); // Use String's matches method directly for clarity
    }

    public static void menuEmail() {
        try (Scanner scanner = new Scanner(System.in)) {
            String userInput;

            while (true) {
                System.out.println();
                System.out.println("Input email (or 0 to go back):");

                userInput = scanner.nextLine(); // Read the input as a string to preserve leading zeros

                if (userInput.equals("0")) {
                    proceedToPayment();
                    break; // Correctly manage the loop exit
                } else if (isValidEmail(userInput)) { // Validate the email
                    email = userInput; // Save the valid email
                    LocalDate today = LocalDate.now();
                    PaypalPayment payment = new PaypalPayment(today, email);
                    Receipt receipt = payment.processPayment(cart.calculateTotal(), customer.getAddress());
                    receipt.printReceipt();
                    cart.finalizePurchase();
                    menuPostPayment();
                    break; // Exit after successful operations
                } else {
                    System.out.println("Invalid input. Please enter a valid email.");
                }
            }
        } // No need to manually close the scanner; it's handled by try-with-resources
    }
    
    public static void menuPostPayment() {
        try (Scanner scanner = new Scanner(System.in)) {
            int userInput;

            while (true) {
                System.out.println();
                System.out.println("Input 0 to go back to the main menu");
                System.out.print("Enter your choice: ");

                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();
                    switch (userInput) {
                        case 0:
                            userMainMenu();  // Navigate back to the main menu
                            return; // Exit the method completely after navigation
                        default:
                            System.out.println("Invalid input. Please enter '0' to return to the main menu.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();  // Clear the non-integer input from the scanner buffer
                }
            }
        } // The scanner is automatically closed here due to try-with-resources
    }
}
