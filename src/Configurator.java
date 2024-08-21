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

public class Configurator {
    private static Customer customer;
    private static Administrator admin;

    public static void listUserDetails() {
        String filePath = "UserAccounts.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("User Details:");
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(", ");
                // Displaying selected details, excluding house number, postcode, and city
                System.out.println("User ID: " + userDetails[0] +
                                   ", Username: " + userDetails[1] +
                                   ", Name: " + userDetails[2] +
                                   ", Role: " + userDetails[6]);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    public static void createUserObject(String userID) {
        String filePath = "UserAccounts.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(", ");
                if (userDetails[0].equals(userID)) {
                    if (userDetails[6].trim().equals("customer")) {
                    	Address address = new Address(userDetails[3].trim(), userDetails[4].trim(), userDetails[5].trim());
                        customer = new Customer(Integer.parseInt(userDetails[0].trim()), userDetails[1].trim(), userDetails[2].trim(), address);
                    } else if (userDetails[6].trim().equals("admin")) {
                    	Address address = new Address(userDetails[3].trim(), userDetails[4].trim(), userDetails[5].trim());
                        admin = new Administrator(Integer.parseInt(userDetails[0].trim()), userDetails[1].trim(), userDetails[2].trim(), address);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

   
    public static void main(String[] args) {
    	new Configurator();
    	Configurator.listUserDetails();
        Scanner scanner = new Scanner(System.in);

        while (customer == null && admin == null) {
            System.out.print("Please enter the User ID to select an account: ");
            String userId = scanner.nextLine();
            createUserObject(userId);
            if (customer == null && admin == null) {
                System.out.println("Invalid User ID, please try again.");
            }
        }

        if (customer != null) {
            System.out.println("Selected User: " + customer.name + customer.name + " (customer)");
            new UserMenu(customer); // If needed to initialize with customer
            UserMenu.userMainMenu();  // Navigate to user menu
        } else if (admin != null) {
            System.out.println("Selected User: " + admin.name + admin.name + " (admin)");
            new AdminMenu(admin); // If needed to initialize with admin
            AdminMenu.adminMainMenu();  // Navigate to admin menu
        }

        scanner.close();
    }
}

    
    