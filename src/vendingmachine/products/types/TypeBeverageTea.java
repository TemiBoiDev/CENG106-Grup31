package vendingmachine.products.types;


public class TypeBeverageTea extends TypeBeverageHot{
    private String teaOrigin;
    private int brewingTimeSec;
    public TypeBeverageTea(int id, String slotId, String name, double price,
                       int stockQuantity, int volumeMl, int servingTempCelsius,
                       String teaOrigin, int brewingTimeSec) {
        super(id, slotId, name, price, stockQuantity, volumeMl, servingTempCelsius);
        this.teaOrigin = teaOrigin;
        this.brewingTimeSec = brewingTimeSec;
    }    public String getTeaOrigin() {
          return teaOrigin; 
}    public int getBrewingTimeSec() { 
          return brewingTimeSec;
 }
}
