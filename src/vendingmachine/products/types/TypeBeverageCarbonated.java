package vendingmachine.products.types;

public abstract class TypeBeverageCarbonated extends TypeBeverageCold {
    private double carbonationLevel;    
    public TypeBeverageCarbonated(int id, String slotId, String name, double price,
                              int stockQuantity, int volumeMl, int storageTempCelsius,
                              double carbonationLevel) {
        super(id, slotId, name, price, stockQuantity, volumeMl, storageTempCelsius);
        this.carbonationLevel = carbonationLevel;
    }    public double getCarbonationLevel() {
            return carbonationLevel;
 }
  } 
