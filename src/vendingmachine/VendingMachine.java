package vendingmachine;

import vendingmachine.data.FileManager;
import vendingmachine.products.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {

    public static final int LOW_STOCK_THRESHOLD = 3;

    private final Map<String, AbstractProduct> inventory = new LinkedHashMap<>();

    private double totalRevenue = 0.0;

    private static VendingMachine instance;

    public static VendingMachine getInstance() {
        if (instance == null) {
            instance = new VendingMachine();
        }
        return instance;
    }
    private VendingMachine() {
        FileManager fm = new FileManager();
        Map<String, AbstractProduct> loadedInventory = fm.loadInventory();
        
        // Eğer dosya boşsa veya okunamadıysa varsayılan ürünleri ekle
        if (loadedInventory != null && !loadedInventory.isEmpty()) {
            this.inventory.putAll(loadedInventory);
        } else {
            inventory.put("A1", new ProductFood(1, "A1", "Chocolate", 1.50, 10, 250));
            inventory.put("B1", new ProductBeverage(2, "B1", "Water", 0.75, 15, true));
            inventory.put("C1", new ProductTechnology(3, "C1", "Earphones", 19.99, 3, "SoundMax"));
            fm.saveInventory(inventory);
        }
        this.totalRevenue = fm.getVaultBalance();
    }

    public boolean isAvailable(String slot) {
        AbstractProduct p = inventory.get(normalise(slot));
        return p != null && p.isAvailable();
    }

    public double getPrice(String slot) {
        AbstractProduct p = inventory.get(normalise(slot));
        return (p == null) ? -1.0 : p.getPrice();
    }

    public void dispense(String slot) {
        getProductOrThrow(slot).decreaseStock();
    }

    public void addRevenue(double amount) {
        if (amount > 0) totalRevenue += amount;
    }

    public void restock(String slot, int amount) {
        getProductOrThrow(slot).increaseStock(amount);
    }

    public void reprice(String slot, double newPrice) {
        getProductOrThrow(slot).setPrice(newPrice);
    }

    public void addProduct(String slot, AbstractProduct product) {
        if (slot == null || slot.isBlank())
            throw new IllegalArgumentException("Slot key cannot be empty.");
        if (product == null)
            throw new IllegalArgumentException("Product cannot be null.");
        inventory.put(normalise(slot), product);
    }

    public double collectRevenue() {
        double collected = totalRevenue;
        totalRevenue = 0.0;
        return collected;
    }

    public Map<String, AbstractProduct> getInventory() {
        return new LinkedHashMap<>(inventory);
    }

    public List<AbstractProduct> getLowStockProducts() {
        List<AbstractProduct> result = new ArrayList<>();
        for (AbstractProduct p : inventory.values()) {
            if (p.getStock() <= LOW_STOCK_THRESHOLD) result.add(p);
        }
        return result;
    }

    public double getTotalRevenue() { return totalRevenue; }

    public AbstractProduct getProductOrThrow(String slot) {
        AbstractProduct p = inventory.get(normalise(slot));
        if (p == null)
            throw new ProductNotFoundException(
                "No product found in slot '" + slot + "'.");
        return p;
    }

    public void printInventory() {
        System.out.println("\n  --- CURRENT INVENTORY ---");
        System.out.printf("  %-5s %-22s %8s  %6s  %s%n",
                "SLOT", "Name", "Price", "Stock", "Status");
        System.out.println("  " + "-".repeat(58));

        for (Map.Entry<String, AbstractProduct> entry : inventory.entrySet()) {
            AbstractProduct p = entry.getValue();
            String status;
            if (!p.isAvailable())              status = "[EMPTY]";
            else if (p.getStock() <= LOW_STOCK_THRESHOLD) status = "[LOW]  ";
            else                               status = "[OK]   ";

            System.out.printf("  %-5s %-22s $%6.2f  %5d  %s%n",
                    entry.getKey(), p.getName(),
                    p.getPrice(), p.getStock(), status);
        }
        System.out.printf("%n  Cash box total : $%.2f%n", totalRevenue);
    }

    private String normalise(String slot) {
        return (slot == null) ? "" : slot.trim().toUpperCase();
    }
}
