package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeFoodSavoury;

public class ItemChips extends TypeFoodSavoury {
    private String flavour;         
    public ItemChips(int id, String slotId, double price, int stockQuantity,
                 int calorieCount, double weightGrams, String brand,
                 String saltLevel, String flavour) {
        super(id, slotId, "Chips - " + flavour, price, stockQuantity,
              calorieCount, weightGrams, brand, saltLevel);
        this.flavour = flavour;
    }    public String getFlavour() {
                return flavour;
 }    @Override
    public String toString() {
        return super.toString() +
               ", flavour=" + flavour;    
}
}
