package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeFoodSweet;

public class ItemBiscuit extends TypeFoodSweet {
     private boolean hasCream;       
    public ItemBiscuit(int id, String slotId, double price, int stockQuantity,
                         int calorieCount, double weightGrams, String brand,
                         String sweetenerType, boolean hasCream) {
        super(id, slotId, "BiscuitPacket", price, stockQuantity,
              calorieCount, weightGrams, brand, sweetenerType);

        this.hasCream = hasCream;
    }    public boolean isHasCream() {
                 return hasCream;
        }
    @Override
    public String toString() {
        return super.toString() +
               ", hasCream=" + hasCream;    
}
}
