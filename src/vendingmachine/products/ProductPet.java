package vendingmachine.products;

public class ProductPet extends AbstractProduct{
    private String petType ;
    
    public ProductPet (int id, String slotId, String name, double price, int stockQuantity, String petType){
        super(id, slotId, name, price, stockQuantity);
        this.petType = petType;
        
    }
    public String getPetType(){
        return petType;
    }
}
