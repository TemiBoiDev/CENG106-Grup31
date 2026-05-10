package vendingmachine.products.types.items;

import vendingmachine.products.ProductTechnology;

public class ItemPowerbank extends ProductTechnology {
    private int capacityMah;            
    private int usbPortCount;          
    private boolean supportsWireless;   
    public ItemPowerbank(int id, String slotId, double price, int stockQuantity,
                     int capacityMah, int usbPortCount, boolean supportsWireless) {
        super(id, slotId, "Powerbank " + capacityMah + "mAh", price, stockQuantity, "Powerbank");
        this.capacityMah = capacityMah;
        this.usbPortCount = usbPortCount;
        this.supportsWireless = supportsWireless;
    }    public int getCapacityMah() {
           return capacityMah;
 }    public int getUsbPortCount() { 
            return usbPortCount; 
 }    public boolean isSupportsWireless() {
           return supportsWireless;
 }    @Override
    public String toString() {
        return super.toString() +
               ", capacity=" + capacityMah + "mAh" +
               ", usbPorts=" + usbPortCount +
               ", wireless=" + supportsWireless;
    }
}
