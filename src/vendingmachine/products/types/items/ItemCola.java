package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeBeverageCarbonated;

public class ItemCola extends TypeBeverageCarbonated {
    private boolean isSugarFree;    
    public ItemCola(int id, String slotId, double price, int stockQuantity,
                          int volumeMl, double carbonationLevel, boolean isSugarFree) {
        super(id, slotId, isSugarFree ? "Cola Zero" : "Cola", price,
              stockQuantity, volumeMl, 4, carbonationLevel);
        this.isSugarFree = isSugarFree;
    }    public boolean isSugarFree() {
             return isSugarFree;
 }    @Override
    public String toString() { 
       return super.toString() +
               ", carbonation=" + getCarbonationLevel() +
               ", sugarFree=" + isSugarFree;
  }
}
