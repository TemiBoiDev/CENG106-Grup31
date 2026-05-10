package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeBeverageNonCarbonated;

public class ItemJuice extends TypeBeverageNonCarbonated{
    private String fruitType;       
    private int juicePercent;       
   public ItemJuice(int id, String slotId, double price, int stockQuantity,
                              int volumeMl, boolean isNatural,
                              String fruitType, int juicePercent) {
        super(id, slotId, fruitType + " Juice", price,
              stockQuantity, volumeMl, 4, isNatural);
        this.fruitType = fruitType; 
       this.juicePercent = juicePercent;
    }
    public String getFruitType() {
        return fruitType;
 }    public int getJuicePercent() {
            return juicePercent;
 }    @Override
    public String toString() {
        return super.toString() +
               ", fruit=" + fruitType +
               ", juice%=" + juicePercent;
    }
}
