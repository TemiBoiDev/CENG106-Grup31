package vendingmachine.products.types.items;

import vendingmachine.products.types.TypeBeverageHot;

public class ItemCoffee extends TypeBeverageHot{
    private String roastLevel;      
    private boolean hasMilk;        
    public ItemCoffee(int id, String slotId, String name, double price,
                          int stockQuantity, int volumeMl, int servingTempCelsius,
                          String roastLevel, boolean hasMilk) {
        super(id, slotId, name, price, stockQuantity, volumeMl, servingTempCelsius);
        this.roastLevel = roastLevel;
        this.hasMilk = hasMilk;
    }    public String getRoastLevel() {
            return roastLevel;
 }
    public boolean isHasMilk() {
       return hasMilk; 
    }
}
