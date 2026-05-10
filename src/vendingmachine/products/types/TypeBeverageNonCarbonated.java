package vendingmachine.products.types;

    public abstract class TypeBeverageNonCarbonated extends TypeBeverageCold {
    private boolean isNatural;     
    public TypeBeverageNonCarbonated(int id, String slotId, String name, double price,
                                 int stockQuantity, int volumeMl, int storageTempCelsius,
                                 boolean isNatural) { 
       super(id, slotId, name, price, stockQuantity, volumeMl, storageTempCelsius);
        this.isNatural = isNatural;
    }    public boolean isNatural() {
          return isNatural; 
}
  } 