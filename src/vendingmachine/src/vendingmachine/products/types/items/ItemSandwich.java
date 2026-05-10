package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeFoodSavoury;

public class ItemSandwich extends TypeFoodSavoury {
    private String fillingType;    
    private boolean isToasted;     
    public ItemSandwich(int id, String slotId, double price, int stockQuantity,
                    int calorieCount, double weightGrams, String brand,
                    String saltLevel, String fillingType, boolean isToasted) {
        super(id, slotId, "Sandwich - " + fillingType, price, stockQuantity,
              calorieCount, weightGrams, brand, saltLevel);
        this.fillingType = fillingType;
        this.isToasted = isToasted;
    }    public String getFillingType() {
             return fillingType;
 }
    public boolean isToasted() {
          return isToasted;
 }    @Override
    public String toString() {
        return super.toString() +
               ", filling=" + fillingType +
               ", toasted=" + isToasted;
    } 
}
