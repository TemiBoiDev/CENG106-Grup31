package vendingmachine.products.types;

public abstract class TypeFoodSavoury extends TypeFoodPackaged{
    private String saltLevel;       
    public TypeFoodSavoury(int id, String slotId, String name, double price,
                       int stockQuantity, int calorieCount,
                       double weightGrams, String brand, String saltLevel) {
        super(id, slotId, name, price, stockQuantity, calorieCount, weightGrams, brand);
        this.saltLevel = saltLevel;
    }    public String getSaltLevel() {
          return saltLevel;
    }
}
