package vendingmachine.logic;

import vendingmachine.VendingMachine;
import vendingmachine.data.FileManager;
import vendingmachine.products.AbstractProduct;
import vendingmachine.OutOfStockException;
import vendingmachine.ProductNotFoundException;

public class LogicUser {
    public boolean checkAvailability(String slot){
            VendingMachine machine = VendingMachine.getInstance();
            return machine.isAvailable(slot);
        }
    public double getItemPrice(String slot){
        VendingMachine machine = VendingMachine.getInstance();
        return machine.getPrice(slot);
    }
    //Processing change happens in GUI
    public boolean processSale(String slot){
        try {
            VendingMachine machine = VendingMachine.getInstance();
            
            //gather data
            AbstractProduct item = machine.getProductOrThrow(slot);
            double price = machine.getPrice(slot);
            String itemName = item.getName();
            //Perform Job
            machine.dispense(slot);
            machine.addRevenue(price);
            //File Management
            FileManager fm = new FileManager();
            fm.saveTransaction(machine.getInventory(), itemName, price);
            return true;
            
            } catch (OutOfStockException | ProductNotFoundException e) {    
                // Log the error to the console for debugging
                System.err.println("Sale failed to process: " + e.getMessage());
                
                return false;
            }
    }
}
