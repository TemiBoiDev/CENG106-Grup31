package vendingmachine.products.types.items;

import vendingmachine.products.ProductTechnology;

public class ItemAirpods extends ProductTechnology {
    private String model;              
    private boolean hasNoiseCancelling;
    private int batteryLifeHours;       
    public ItemAirpods(int id, String slotId, double price, int stockQuantity,
                   String model, boolean hasNoiseCancelling, int batteryLifeHours) {
        super(id, slotId, model, price, stockQuantity, "AirPods");

        this.model = model;
        this.hasNoiseCancelling = hasNoiseCancelling;
        this.batteryLifeHours = batteryLifeHours;
    }    public String getModel() {
              return model;
 }    public boolean isHasNoiseCancelling() {
           return hasNoiseCancelling;
 }
      public int getBatteryLifeHours() {
        return batteryLifeHours;
 }
    @Override
    public String toString() {
        return super.toString() +
               ", model=" + model +
               ", noiseCancelling=" + hasNoiseCancelling +
               ", batteryLife=" + batteryLifeHours + "h";
    } 
}
