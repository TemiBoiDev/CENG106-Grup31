package vendingmachine.products.types;

import vendingmachine.products.ProductBeverage;

public abstract class TypeBeverageHot extends ProductBeverage {
    private int servingTempCelsius;     
    public TypeBeverageHot(int id, String slotId, String name, double price,
                       int stockQuantity, int volumeMl, int servingTempCelsius) {
        super(id, slotId, name, price, stockQuantity, false, volumeMl);  // ADD false HERE
        this.servingTempCelsius = servingTempCelsius; 
    }    
    public int getServingTempCelsius() {
        return servingTempCelsius;
    }
}
