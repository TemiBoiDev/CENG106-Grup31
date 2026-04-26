package vendingmachine.products;

import vendingmachine.OutOfStockException;

public abstract class AbstractProduct {
    private int id;
    private String slotId;
    private String name;
    private double price;
    private int stockQuantity;
     
    public AbstractProduct(int id, String slotId, String name, double price, int stockQuantity){
        this.id = id;
        this.slotId = slotId;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    public int getId() { return id; }
    public String getSlotId() { return slotId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
   
    public void updateStock(int amount) {
        this.stockQuantity += amount;
    }
    
    public String toString() {
        return "id=" + id + ", slot=" + slotId + ", name=" + name +
               ", price=" + price + ", stock=" + stockQuantity;
    }

    public boolean isAvailable() {
        return stockQuantity > 0;
    }

    // DÜZELTME 1: Stok bittiğinde özel hatamızı fırlatıyoruz
    public void decreaseStock() {
        if (stockQuantity <= 0) {
            throw new OutOfStockException("Stokta ürün kalmadı!");
        }
        stockQuantity--;
    }

    public void increaseStock(int amount) {
        if(amount > 0) {
            this.stockQuantity += amount;
        }
    }

    public void setPrice(double newPrice) {
        if(newPrice >= 0) {
            this.price = newPrice;
        }
    }

    public void displayInfo() {
        System.out.println(this.toString());
    }

    // DÜZELTME 2: Exception fırlatan hatalı kod düzeltildi
    public int getStock() {
        return this.stockQuantity; 
    }
}

