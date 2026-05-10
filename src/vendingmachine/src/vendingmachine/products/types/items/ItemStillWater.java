package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeBeverageNonCarbonated;

public class ItemStillWater extends TypeBeverageNonCarbonated {
    private String source;          
    public ItemStillWater(int id, String slotId, double price, int stockQuantity,
                      int volumeMl, String source) {
        super(id, slotId, "StillWater - " + source, price,
              stockQuantity, volumeMl, 4, true);
        this.source = source; 
   }    public String getSource() {
           return source;
 }    @Override
    public String toString() {
        return super.toString() +
               ", source=" + source; 
   } 
}
