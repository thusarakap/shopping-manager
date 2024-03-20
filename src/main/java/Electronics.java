// Extends class from the product which contains Electronic items

public class Electronics extends Product{
    private int warranty;
    private String brandName;

    public Electronics(int productId,String productName, double price, int stock, int warranty, String brandName){
        super(productId, productName, price, stock);
        this.warranty=warranty;
        this.brandName=brandName;
    }

    // Getter for get the productId
    public String getBrandName() {
        return this.brandName;
    }

    // Getter for get the productId
    public int getWarranty() {
        return this.warranty;
    }

    // override the method to include warranty details
    @Override
    public String getDetails() {
        return "Electronics - " + super.getDetails() + ", Warranty: " + warranty + ", Brand: "+ brandName;
    }
}
