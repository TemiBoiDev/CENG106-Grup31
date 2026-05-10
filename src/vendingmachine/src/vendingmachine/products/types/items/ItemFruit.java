package vendingmachine.products.types.items;

import vendingmachine.products.ProductFood;

public class ItemFruit extends ProductFood {
    private String fruitName;       
    private boolean isOrganic;      
    private double weightGrams;     
    public ItemFruit(int id, String slotId, double price, int stockQuantity,
                        int calorieCount, String fruitName,
                        boolean isOrganic, double weightGrams) { 
       super(id, slotId, fruitName, price, stockQuantity, calorieCount);
        this.fruitName = fruitName;
        this.isOrganic = isOrganic;
        this.weightGrams = weightGrams;
    }    public String getFruitName() {
           return fruitName;
 }    public boolean isOrganic() {
            return isOrganic;
 }    public double getWeightGrams() { 
          return weightGrams;
 }
    @Override 
   public String toString() { 
       return super.toString() + 
              ", fruit=" + fruitName +
               ", organic=" + isOrganic + 
              ", weight=" + weightGrams + "g"; 
   }
}
