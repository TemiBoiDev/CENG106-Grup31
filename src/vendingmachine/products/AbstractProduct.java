package vendingmachine.products;

import vendingmachine.OutOfStockException;

public  abstract class AbstractProduct {
    private int id ;
    private String slotId ;
    private String name ;
    private double price;
    private int stockQuantity ;
     
    public AbstractProduct( int id, String slotId, String name ,double price, int stockQuantity){
        this.id = id ;
        this.slotId = slotId ;
        this.name = name;
        this.price = price ;
        this.stockQuantity = stockQuantity ;
        
  
    }
    
    public int getId(){
        return id ;
    }
    public String getSlotId(){
        return slotId ;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price ;
    }
    public int getStockQuantity(){
        return stockQuantity;
    }
   
    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Restock amount must be positive.");
    }
        this.stockQuantity += amount;
    }
    public void decreaseStock() {
        if (stockQuantity <= 0) {
            throw new OutOfStockException("Product is out of stock!");
        }
        stockQuantity--;
    }
    
    public boolean isAvailable() {
        return stockQuantity > 0;
    }
    public void setPrice(double newPrice) {
        if(newPrice >= 0) {
            this.price = newPrice;
        }
    }
    
    public String toString(){
        return "id=" + id + ",slot=" + slotId + ", name=" + name +
                ", price=" +price+ ", stock=" + stockQuantity ;
    }
    public void displayInfo() {
        System.out.println(this.toString());
    }
    
}
