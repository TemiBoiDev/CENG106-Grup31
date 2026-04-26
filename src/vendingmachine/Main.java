package vendingmachine;

import vendingmachine.logic.ChangeCalculator;
import vendingmachine.logic.ChangeCalculator.PaymentMethod;
import vendingmachine.logic.ChangeCalculator.PaymentResult;
import vendingmachine.logic.LogicAdmin;
import vendingmachine.logic.LogicUser; 

import java.util.Scanner;

public class Main {
    private static VendingMachine machine;
    private static LogicAdmin     adminLogic;
    private static LogicUser      userLogic;
    private static Scanner        scanner;

    public static void main(String[] args) {
        // DÜZELTME: Scanner her şeyden önce başlatılmalı!
        scanner    = new Scanner(System.in); 
        machine    = VendingMachine.getInstance();   
        adminLogic = new LogicAdmin();               
        userLogic  = new LogicUser();                

        printWelcomeBanner();

        boolean running = true;
        while (running) {
            printRoleMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": runCustomerSession(); break;
                case "2": runAdminSession(); break;
                case "0":
                    System.out.println("\n  System shutting down. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("  [X] Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void runCustomerSession() {
        boolean sessionActive = true;
        while (sessionActive) {
            printCustomerMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": machine.printInventory(); break;
                case "2": viewProductDetail(); break;
                case "3": purchaseFlow(); break;
                case "0":
                    System.out.println("  Thank you! Come again.");
                    sessionActive = false;
                    break;
                default: System.out.println("  [X] Invalid option.");
            }
        }
    }

    private static void viewProductDetail() {
        System.out.print("  Enter slot (e.g. A1): ");
        String slot = scanner.nextLine().trim();
        try {
            machine.getProductOrThrow(slot).displayInfo();
        } catch (ProductNotFoundException e) {
            System.out.println("  [X] " + e.getMessage());
        }
    }

    private static void purchaseFlow() {
        System.out.print("  Enter slot to purchase (e.g. B2): ");
        String slot = scanner.nextLine().trim().toUpperCase();

        if (!userLogic.checkAvailability(slot)) {
            System.out.println("  [X] That slot is unavailable or out of stock.");
            return;
        }

        double price = userLogic.getItemPrice(slot);
        if (price < 0) {
            System.out.println("  [X] Could not retrieve price for slot " + slot + ".");
            return;
        }
        System.out.printf("  Price: $%.2f%n", price);

        PaymentMethod method = choosePaymentMethod();
        if (method == null) return;         

        double amountTendered = price;      

        if (method == PaymentMethod.CASH) {
            System.out.printf("  Insert cash (price is $%.2f): $", price);
            try {
                amountTendered = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [X] Invalid amount entered.");
                return;
            }
        }

        PaymentResult result = ChangeCalculator.processPayment(price, amountTendered, method);
        result.printReceipt();

        if (!result.isSuccess()) return;                         

        boolean sold = userLogic.processSale(slot, result.getAmountPaid());

        if (sold) {
            System.out.println("\n  Enjoy your purchase!");
        } else {
            System.out.println("  [X] System Error: Could not process sale.");
        }
    }

    private static PaymentMethod choosePaymentMethod() {
        System.out.println("\n  Select payment method:");
        System.out.println("  1. Cash");
        System.out.println("  2. Credit Card");
        System.out.println("  3. Bank Card (Debit)");
        System.out.println("  0. Cancel");
        System.out.print  ("  Choice: ");

        switch (scanner.nextLine().trim()) {
            case "1": return PaymentMethod.CASH;
            case "2": return PaymentMethod.CREDIT_CARD;
            case "3": return PaymentMethod.BANK_CARD;
            case "0": System.out.println("  Purchase cancelled."); return null;
            default : System.out.println("  [X] Invalid choice."); return null;
        }
    }

    private static void runAdminSession() {
        if (!adminLogic.verifyAdmin()) {
            return;
        }
        boolean active = true;
        while(active) {
            System.out.println("\n  +-------------------------------+");
            System.out.println("  |   ADMIN MENU                  |");
            System.out.println("  +-------------------------------+");
            System.out.println("  |  1. Restock Item              |");
            System.out.println("  |  2. Reprice Item              |");
            System.out.println("  |  3. Add New Product           |");
            System.out.println("  |  4. Change Password           |");
            System.out.println("  |  5. View Total Revenue        |");
            System.out.println("  |  6. Collect Revenue           |");
            System.out.println("  |  7. Check Low Stock           |");
            System.out.println("  |  0. Exit Admin Mode           |");
            System.out.println("  +-------------------------------+");
            System.out.print  ("  Choice: ");
            String choice = scanner.nextLine().trim();
            
            if (choice.equals("1")) {
                try { 
                    System.out.print("  Enter slot: ");
                    String slot = scanner.nextLine();
                    System.out.print("  Enter amount: ");
                    int amt = Integer.parseInt(scanner.nextLine());
                    adminLogic.restock(slot, amt);
                    System.out.println("  Restocked!");
                } catch (ProductNotFoundException e){
                    System.out.println("  [X]    " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("  [X] Please enter a valid number for the amount");
                }
            } else if (choice.equals("2")) {
                try {
                    System.out.print("  Enter slot: ");
                    String slot = scanner.nextLine();
                    System.out.print("  Enter new price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    adminLogic.reprice(slot, price);
                    System.out.println("  Repriced!");
                } catch (ProductNotFoundException e){
                    System.out.println("  [X] " + e.getMessage());
                } catch (NumberFormatException e){
                    System.out.println("  [X] Please enter a valid number for the price.");
                }
            } else if (choice.equals("3")){
                try {
                    System.out.println("  Enter new slot (e.g. D1)");
                    String slot = scanner.nextLine().toUpperCase();
                    System.out.print("  Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("  Enter price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("  Enter stock amount: ");
                    int stock = Integer.parseInt(scanner.nextLine());
                    adminLogic.addNewProduct(slot, name, price, stock);
                    System.out.println("  Product added to slot " + slot + "!");
                    } catch (NumberFormatException e) {
                    System.out.println("  [X] Please enter valid numbers for price and stock.");
                }
            } else if (choice.equals("4")){
                System.out.print("  Enter new admin password: ");
                String newPass = scanner.nextLine();
                adminLogic.changePassword(newPass);
                System.out.println("  Password updated successfully!");
            } else if (choice.equals("5")) {
                System.out.printf("  Current Revenue in machine: $%.2f%n", adminLogic.getTotalRevenue());
            } else if (choice.equals("6")) {
                double collected = adminLogic.collectRevenue();
                System.out.printf("  Successfully collected $%.2f! The vault is now empty.%n", collected);
            } else if (choice.equals("7")) {
                adminLogic.checkLowStock();
            } else if (choice.equals("0")) {
                active = false;
            } else {
                System.out.println("  Invalid choice.");
            }
        }
    }

    private static void printWelcomeBanner() {
        System.out.println("\n  +================================================+");
        System.out.println("  |    MULTI-PURPOSE VENDING MACHINE  v1.0         |");
        System.out.println("  |    CENG106 OOP Project  |  Group 31            |");
        System.out.println("  +================================================+");
    }

    private static void printRoleMenu() {
        System.out.println("\n  +==============================+");
        System.out.println("  |  Who are you?                |");
        System.out.println("  |  1. Customer                 |");
        System.out.println("  |  2. Admin / Manager          |");
        System.out.println("  |  0. Shut Down                |");
        System.out.println("  +==============================+");
        System.out.print  ("  Select: ");
    }

    private static void printCustomerMenu() {
        System.out.println("\n  +-------------------------------+");
        System.out.println("  |   CUSTOMER MENU               |");
        System.out.println("  +-------------------------------+");
        System.out.println("  |  1. Browse Products           |");
        System.out.println("  |  2. View Product Detail       |");
        System.out.println("  |  3. Buy a Product             |");
        System.out.println("  |  0. Exit                      |");
        System.out.println("  +-------------------------------+");
        System.out.print  ("  Choice: ");
    }
}