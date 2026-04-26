package vendingmachine;

import vendingmachine.data.FileManager;
import vendingmachine.products.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * =========================================================================
 *  VENDING MACHINE  —  Central Data Model  (MVC: Model Layer)
 * =========================================================================
 *
 *  This class is the SINGLE SOURCE OF TRUTH for the entire application.
 *  Both AdminLogic and UserLogic must route through the methods here
 *  to read or mutate inventory data.
 *
 *  SLOT SYSTEM
 *  -----------
 *  Every product occupies a named slot  (e.g. "A1", "B2", "C3").
 *  Slot keys are case-insensitive ("a1" == "A1").
 *
 *  ┌─────────────────────────────────────────────────────────────────┐
 *  │  HOW TEAM MEMBERS CONNECT TO THIS CLASS                        │
 *  │                                                                 │
 *  │  AdminLogic  ──►  restock(slot, amount)                        │
 *  │                   reprice(slot, newPrice)                       │
 *  │                   addProduct(slot, product)                     │
 *  │                   collectRevenue()                              │
 *  │                   getLowStockProducts()                         │
 *  │                                                                 │
 *  │  UserLogic   ──►  isAvailable(slot)                            │
 *  │                   getPrice(slot)                               │
 *  │                   dispense(slot)   ← call AFTER payment OK     │
 *  │                   addRevenue(amount)                            │
 *  │                                                                 │
 *  │  ChangeCalculator ── standalone utility, call from Main or     │
 *  │                       UserLogic after payment amount is known.  │
 *  └─────────────────────────────────────────────────────────────────┘
 * =========================================================================
 */
public class VendingMachine {

    // ── Constants ─────────────────────────────────────────────────────
    /** Products at or below this stock count are flagged as low. */
    public static final int LOW_STOCK_THRESHOLD = 3;

    // ── Inventory  (SLOT → Product) ───────────────────────────────────
    private final Map<String, AbstractProduct> inventory = new LinkedHashMap<>();

    // ── Financial state ───────────────────────────────────────────────
    private double totalRevenue = 0.0;

    // ── Shared instance ───────────────────────────────────────────────
    /**
     * Optional singleton accessor.
     * Lets AdminLogic and UserLogic obtain the same machine object
     * without needing a constructor argument. Teams may also simply
     * pass the instance through the constructor — both styles work.
     *
     * Usage:  VendingMachine vm = VendingMachine.getInstance();
     */
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

  
  

    // =========================================================================
    //  READ OPERATIONS  (UserLogic connects here)
    // =========================================================================

    /**
     * Returns true when the slot exists AND has at least 1 unit in stock.
     * Direct implementation target for UserLogic.checkAvailability(slot).
     * @param slot
     * @return 
     */
    public boolean isAvailable(String slot) {
        AbstractProduct p = inventory.get(normalise(slot));
        return p != null && p.isAvailable();
    }

    /**
     * Returns the price of the product in the given slot.
     * Returns -1.0 if the slot does not exist.
     * Direct implementation target for UserLogic.getItemPrice(slot).
     * @param slot
     * @return 
     */
    public double getPrice(String slot) {
        AbstractProduct p = inventory.get(normalise(slot));
        return (p == null) ? -1.0 : p.getPrice();
    }

    /**
     * Physically dispenses one unit from the slot (stock -= 1).
     * Call this ONLY after payment has been confirmed successful.
     * Maps to the inventory-reduction step inside UserLogic.processSale().
     *
     * @param slot
     * @throws OutOfStockException      if stock is already 0
     * @throws ProductNotFoundException if the slot does not exist
     */
    public void dispense(String slot) {
        getProductOrThrow(slot).decreaseStock();
    }

    /**
     * Records revenue after a confirmed sale (cash or card).
     * Call this once per successful transaction.
     * @param amount
     */
    public void addRevenue(double amount) {
        if (amount > 0) totalRevenue += amount;
    }

    // =========================================================================
    //  WRITE OPERATIONS  (AdminLogic connects here)
    // =========================================================================

    /**
     * Increases stock of an existing slot.
     * Direct implementation target for AdminLogic.restock(slot, amountChanged).
     *
     * @param slot
     * @param amount
     * @throws ProductNotFoundException if slot does not exist
     * @throws IllegalArgumentException if amount <= 0
     */
    public void restock(String slot, int amount) {
        getProductOrThrow(slot).increaseStock(amount);
    }

    /**
     * Updates the unit price of a product.
     * Direct implementation target for AdminLogic.reprice(slot, newPrice).
     *
     * @throws ProductNotFoundException if slot does not exist
     * @throws IllegalArgumentException if newPrice < 0
     */
    public void reprice(String slot, double newPrice) {
        getProductOrThrow(slot).setPrice(newPrice);
    }

    /**
     * Inserts a new product into a slot.
     * If the slot already holds a product it is replaced.
     */
    public void addProduct(String slot, AbstractProduct product) {
        if (slot == null || slot.isBlank())
            throw new IllegalArgumentException("Slot key cannot be empty.");
        if (product == null)
            throw new IllegalArgumentException("Product cannot be null.");
        inventory.put(normalise(slot), product);
    }

    /**
     * Empties the cash box and returns the total collected.
     * Called by AdminLogic when the owner withdraws revenue.
     */
    public double collectRevenue() {
        double collected = totalRevenue;
        totalRevenue = 0.0;
        return collected;
    }

    // =========================================================================
    //  QUERY HELPERS  (AdminPanel / display code)
    // =========================================================================

    /** Defensive copy of the full inventory map. */
    public Map<String, AbstractProduct> getInventory() {
        return new LinkedHashMap<>(inventory);
    }

    /** All products at or below LOW_STOCK_THRESHOLD. */
    public List<AbstractProduct> getLowStockProducts() {
        List<AbstractProduct> result = new ArrayList<>();
        for (AbstractProduct p : inventory.values()) {
            if (p.getStock() <= LOW_STOCK_THRESHOLD) result.add(p);
        }
        return result;
    }

    /** Current revenue without resetting the cash box. */
    public double getTotalRevenue() { return totalRevenue; }

    /**
     * Retrieves a product or throws ProductNotFoundException.
     * Central lookup used by all public methods above.
     */
    public AbstractProduct getProductOrThrow(String slot) {
        AbstractProduct p = inventory.get(normalise(slot));
        if (p == null)
            throw new ProductNotFoundException(
                "No product found in slot '" + slot + "'.");
        return p;
    }

    // =========================================================================
    //  DISPLAY UTILITY
    // =========================================================================

    /** Prints a formatted inventory table to stdout. */
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

    // ── Internal helper ───────────────────────────────────────────────
    private String normalise(String slot) {
        return (slot == null) ? "" : slot.trim().toUpperCase();
    }
}
