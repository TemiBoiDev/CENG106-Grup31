package vendingmachine.products;

public class ProductFood extends AbstractProduct {
    private int calorieCount ;
    
    public ProductFood(int id, String slotId, String name, double price, int stockQuantity, int calorieCount){
        super(id, slotId, name, price, stockQuantity);
        this.calorieCount = calorieCount ;
    }
    
    public int getCalorieCount(){
        return calorieCount;
    }
}
