package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeBeverageTea;

public class ItemTea extends TypeBeverageTea {
    public ItemTea(int id, String slotId, double price, int stockQuantity,
                    int volumeMl, String teaOrigin) { 
       super(id, slotId, "TeaBlack", price, stockQuantity,
              volumeMl, 90, teaOrigin, 180);
    }    @Override
     public String toString() {
        return super.toString() +           
   ", origin=" + getTeaOrigin() +  
             ", brewing=" + getBrewingTimeSec() + "s"; 
     }
}
