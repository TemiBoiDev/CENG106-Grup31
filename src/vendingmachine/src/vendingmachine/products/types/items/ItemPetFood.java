package vendingmachine.products.types.items;

import vendingmachine.products.ProductPet;

public class ItemPetFood extends ProductPet {
    private String foodType;        
    private double weightGrams;     
    private String flavour;         
    public ItemPetFood(int id, String slotId, double price, int stockQuantity,
                   String petType, String foodType, double weightGrams, String flavour) {
       super(id, slotId, "PetFood - " + foodType, price, stockQuantity, petType);
        this.foodType = foodType;
        this.weightGrams = weightGrams;
        this.flavour = flavour;
    }    public String getFoodType() {
           return foodType;
 }    public double getWeightGrams() {
            return weightGrams;
 }    public String getFlavour() {
            return flavour;
 }    @Override
      public String toString() {
        return super.toString() +
               ", foodType=" + foodType +
               ", weight=" + weightGrams + "g" +
               ", flavour=" + flavour;
    }
}
