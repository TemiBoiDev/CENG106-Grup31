package vendingmachine.logic;

import vendingmachine.VendingMachine;
import vendingmachine.data.FileManager;
import vendingmachine.products.AbstractProduct;

public class LogicUser {
    public boolean checkAvailability(String slot){
            VendingMachine machine = VendingMachine.getInstance();
            return machine.isAvailable(slot);
        }
    public double getItemPrice(String slot){
        VendingMachine machine = VendingMachine.getInstance();
        return machine.getPrice(slot);
    }
    public boolean processSale(String slot, double moneyInserted){
        VendingMachine machine = VendingMachine.getInstance();
        
        machine.dispense(slot);
        machine.addRevenue(moneyInserted);
        
        FileManager fm = new FileManager();
        AbstractProduct item = machine.getProductOrThrow(slot);
        
        fm.logSale(item.getName(), machine.getPrice(slot));
        fm.saveInventory(machine.getInventory());
        fm.updateVault(machine.getPrice(slot));
        
        return true;
    }
}
