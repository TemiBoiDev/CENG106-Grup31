package vendingmachine.products;

public class ProductBeverage extends AbstractProduct{
    private boolean isCooled;
    private int volumeMl;
    
    public ProductBeverage(int id, String slotId, String name, double price,
                          int stockQuantity, boolean isCooled, int volumeMl){
        super(id, slotId, name, price, stockQuantity);
        this.isCooled = isCooled;
        this.volumeMl = volumeMl;
    }
    
    public int getVolumeMl() {
        return volumeMl; }
    public boolean isCooled(){
        return isCooled;
    }
    public void setCooled(boolean cooled){
        this.isCooled= cooled;
    }
}

