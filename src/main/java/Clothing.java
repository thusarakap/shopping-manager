// Extends class from the product which contains Clothing items

public class Clothing extends Product{

    private String size;
    private String color;

    public Clothing(int productId, String productName, double price, int stock, String size, String color){
        super(productId,productName,price, stock);
        this.size=size;
        this.color=color;
    }

    public String getColor() {
        return this.color;
    }

    public String getSize() {
        return this.size;
    }

    // override the method to include size details
    @Override
    public String getDetails() {
        return "Clothing - " + super.getDetails() + ", Size: " + size + ", Color: " + color;
    }
}