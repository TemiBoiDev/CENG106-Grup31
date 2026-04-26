package vendingmachine.products;

public class ProductTechnology extends AbstractProduct {
    private String deviceType ;
    
    public ProductTechnology( int id, String slotId, String name, double price, 
                                  int stockQuantity, String deviceType){
        super(id,slotId, name, price,stockQuantity);
        this.deviceType = deviceType ;
    }
    public String getDeviceType(){
        return deviceType;
    }
}