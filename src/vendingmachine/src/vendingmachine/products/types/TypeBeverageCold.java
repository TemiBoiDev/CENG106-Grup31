package vendingmachine.products.types;

import vendingmachine.products.ProductBeverage;

public abstract class TypeBeverageCold extends ProductBeverage {
    private int storageTempCelsius;     
    public TypeBeverageCold(int id, String slotId, String name, double price,
                        int stockQuantity, int volumeMl, int storageTempCelsius) {
        super(id, slotId, name, price, stockQuantity, true, volumeMl);  // ADD true HERE
        this.storageTempCelsius = storageTempCelsius;
    }    
    public int getStorageTempCelsius() {
        return storageTempCelsius;
    }
}