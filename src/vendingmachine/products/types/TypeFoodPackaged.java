package vendingmachine.products.types;

import vendingmachine.products.ProductFood;

public class TypeFoodPackaged extends ProductFood {
    private double weightGrams;     
    private String brand;           
    public TypeFoodPackaged(int id, String slotId, String name, double price,
                        int stockQuantity, int calorieCount,
                        double weightGrams, String brand) {
        super(id, slotId, name, price, stockQuantity, calorieCount);
        this.weightGrams = weightGrams;
        this.brand = brand; 
   }    public double getWeightGrams() {
           return weightGrams;
 }    public String getBrand() {
        return brand;
 }
}
