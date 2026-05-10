package vendingmachine.products.types.items;

import vendingmachine.products.ProductTechnology;

public class ItemCharger extends ProductTechnology {
    private String connectorType;  
    private int wattage;            
    public ItemCharger(int id, String slotId, double price, int stockQuantity,
                   String connectorType, int wattage) {
        super(id, slotId, "Charger (" + connectorType + ")", price, stockQuantity, "Charger");
        this.connectorType = connectorType;
        this.wattage = wattage;
    }    public String getConnectorType() {
                return connectorType;
 }    public int getWattage() {
               return wattage;
 }    @Override
    public String toString() {
        return super.toString() +
               ", connector=" + connectorType +
               ", wattage=" + wattage + "W";
    }
}
