package vendingmachine.logic;

import vendingmachine.VendingMachine;
import vendingmachine.data.FileManager;

import java.util.Scanner;

public class LogicAdmin {
            
    public boolean verifyAdmin(){
        Scanner scanner = new Scanner(System.in);
        FileManager fm = new FileManager();
        
        if (!fm.hasAdminFile()){
            System.out.println("\n [FIRST TIME SETUP] Create an Admin Password:");
            String newPass = scanner.nextLine();
            fm.setAdminPassword(newPass);
            System.out.println("  Password saved! You are now logged in as Admin.");    
            return true;
        }
        System.out.println("\n Please enter Admin Password:");
        String input = scanner.nextLine();
        
        if (fm.checkAdminPassword(input)){
            return true;
        } else {
            System.out.println("    [X] Incorrect Password.");
            return false;
        }
    }    
    public void restock(String slot, int amount){
        VendingMachine machine = VendingMachine.getInstance();
        machine.restock(slot, amount);
        
        
       FileManager fm = new FileManager();
       fm.saveInventory(machine.getInventory());
    }
    public void reprice(String slot, double newPrice){
        VendingMachine machine = VendingMachine.getInstance();
        machine.reprice(slot, newPrice);
        
        FileManager fm = new FileManager();
        fm.saveInventory(machine.getInventory());
    }
    public void changePassword(String newPass){
        FileManager fm = new FileManager();
        fm.setAdminPassword(newPass);
    }
    public void addNewProduct(String slot, String name, double price, int stock){
        VendingMachine machine = VendingMachine.getInstance();
        
        //this part here has been defaulted to food products for the console testing.
        //will be creating a broader perspective when the GUI is added
        vendingmachine.products.ProductFood newProd = new vendingmachine.products.ProductFood(99, slot, name, price, stock, 250);
        machine.addProduct(slot, newProd);
        
        FileManager fm = new FileManager();
        fm.saveInventory(machine.getInventory());
    }
    public double getTotalRevenue(){
        VendingMachine machine = VendingMachine.getInstance();
        return machine.getTotalRevenue();
    }
    
    public double collectRevenue(){
        VendingMachine machine = VendingMachine.getInstance();
        double collected = machine.collectRevenue();
        
        FileManager fm = new FileManager();
        //to update the vault we just substract it all
        fm.updateVault(-collected);
        
        return collected;
    }
    
    public void checkLowStock(){
        VendingMachine machine = VendingMachine.getInstance();
        java.util.List<vendingmachine.products.AbstractProduct> lowStock = machine.getLowStockProducts();
        
        if (lowStock.isEmpty()){
            System.out.println("  All products are sufficiently stocked!");
        } else {
            System.out.println("  [WARNING] The following items are low on stock:");
            for (vendingmachine.products.AbstractProduct p : lowStock){
                System.out.println("  - Slot " + p.getSlotId() + ": " + p.getName() + " (Only " + p.getStock() + " left)");    
            }
        }
        
    }
}
