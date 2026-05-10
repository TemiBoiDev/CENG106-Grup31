package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeFoodSweet;

public class ItemChocolate extends TypeFoodSweet {
    private String chocolateType; 
    public ItemChocolate(int id, String slotId, double price, int stockQuantity,
                        int calorieCount, double weightGrams, String brand,
                        String sweetenerType, String chocolateType) {
        super(id, slotId, "ChocolateBar - " + chocolateType, price, stockQuantity,
              calorieCount, weightGrams, brand, sweetenerType); 
    this.chocolateType = chocolateType;
    }
       public String getChocolateType() {
           return chocolateType;
 }    @Override
    public String toString() {
        return super.toString() +
               ", chocolateType=" + chocolateType;
    } 
}
