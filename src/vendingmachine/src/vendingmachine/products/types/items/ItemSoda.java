package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeBeverageCarbonated;

public class ItemSoda extends TypeBeverageCarbonated {
    private String flavour;         
    public ItemSoda(int id, String slotId, double price, int stockQuantity,
                     int volumeMl, double carbonationLevel, String flavour) {

        super(id, slotId, "SodaWater - " + flavour, price,
              stockQuantity, volumeMl, 4, carbonationLevel);
        this.flavour = flavour;
    }    public String getFlavour() {
               return flavour;
 }    @Override
    public String toString() {
        return super.toString() + 
              ", flavour=" + flavour; 
   }
}
