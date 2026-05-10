package vendingmachine.products.types;

public class TypeFoodSweet extends  TypeFoodPackaged{
    private String sweetenerType;  
    public TypeFoodSweet(int id, String slotId, String name, double price,
                     int stockQuantity, int calorieCount,
                     double weightGrams, String brand, String sweetenerType) {
        super(id, slotId, name, price, stockQuantity, calorieCount, weightGrams, brand);
        this.sweetenerType = sweetenerType;
    }    public String getSweetenerType() {
        return sweetenerType;
 }
}
