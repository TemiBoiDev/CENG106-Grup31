package vendingmachine.products.types.items;

import vendingmachine.products.ProductPet;

public class ItemPetToy extends ProductPet {
    private String material;        
    private String toyType;         
   private boolean isInteractive;  
   public ItemPetToy(int id, String slotId, double price, int stockQuantity,
                  String petType, String material, String toyType, boolean isInteractive) {
        super(id, slotId, "PetToy - " + toyType, price, stockQuantity, petType);
        this.material = material;
        this.toyType = toyType;
        this.isInteractive = isInteractive;
    }    public String getMaterial() {
            return material; 
}    public String getToyType() {
          return toyType;
 }    public boolean isInteractive() {
           return isInteractive;
 }    @Override
        public String toString() {
        return super.toString() +
               ", material=" + material +
               ", toyType=" + toyType +
               ", interactive=" + isInteractive;
    }
}
