package vendingmachine.logic;

import vendingmachine.VendingMachine;
import vendingmachine.data.FileManager;
import vendingmachine.products.types.items.*;

public class LogicAdmin {
               
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
    
    public vendingmachine.products.AbstractProduct addNewProduct(String slot, double price, int stock, String type) throws Exception{
        
        vendingmachine.VendingMachine machine = vendingmachine.VendingMachine.getInstance();
        vendingmachine.products.AbstractProduct newProd = null;
        
        int id = 1; 
        for (vendingmachine.products.AbstractProduct p : machine.getInventory().values()) {
            if (p.getId() >= id) {
                id = p.getId() + 1;
            }
        }
            switch (type) {
                //The variables for the products were hardcoded due to not being able to implement them because of time shortage to the frontend.
                case "FruitProduct": 
                    newProd = new ItemFruit(id, slot, price, stock, 100, "Fruit", true, 150.0); 
                    break;
                case "PetFood": 
                    newProd = new ItemPetFood(id, slot, price, stock, "Dog", "Kibble", 500.0, "Beef"); 
                    break;
                case "PetToy": 
                    newProd = new ItemPetToy(id, slot, price, stock, "Cat", "Plastic", "Mouse", true); 
                    break;
                case "AirPods": 
                    newProd = new ItemAirpods(id, slot, price, stock, "Airpod", true, 24); 
                    break;
                case "Charger": 
                    newProd = new ItemCharger(id, slot, price, stock, "Charger", 20); 
                    break;
                case "Powerbank": 
                    newProd = new ItemPowerbank(id, slot, price, stock, 10000, 2, false); 
                    break;
                case "CarbonatedCola": 
                    newProd = new ItemCola(id, slot, price, stock, 330, 4.5, false); 
                    break;
                case "SodaWater": 
                    newProd = new ItemSoda(id, slot, price, stock, 250, 3.0, "Soda"); 
                    break;
                case "NonCarbonatedJuice": 
                    newProd = new ItemJuice(id, slot, price, stock, 200, true, "Juice", 100); 
                    break;
                case "StillWater": 
                    newProd = new ItemStillWater(id, slot, price, stock, 500, "Spring Water"); 
                    break;
                case "TeaBlack": 
                    newProd = new ItemTea(id, slot, price, stock, 250, "Rize Tea"); 
                    break;
                case "BiscuitPacket": 
                    newProd = new ItemBiscuit(id, slot, price, stock, 400, 100.0, "Generic", "Sugar", true); 
                    break;
                case "ChocolateBar": 
                    newProd = new ItemChocolate(id, slot, price, stock, 250, 50.0, "Generic", "Sugar", "Milk"); 
                    break;
                case "Chips": 
                    newProd = new ItemChips(id, slot, price, stock, 300, 75.0, "Generic", "High", "Salted"); 
                    break;
                case "Sandwich": 
                    newProd = new ItemSandwich(id, slot, price, stock, 350, 150.0, "Generic", "Low", "Cheese", false); 
                    break;
                case "Coffee":
                    newProd = new ItemCoffee(id, slot, "Coffee", price, stock, 250, 85, "Dark", true);
                    break;            
            }
            
            if (newProd != null) {
                machine.addProduct(slot, newProd);
                vendingmachine.data.FileManager fm = new vendingmachine.data.FileManager();
                fm.saveInventory(machine.getInventory());
            } else{
                // Throw a clear error if the dropdown value didn't match the switch statement
                throw new Exception("Invalid product type selected.");
            }
        return newProd;
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
    
}
