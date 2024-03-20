public class Product {
    protected int productId;
    protected String productName;
    protected double price;
    protected int stock;

// Build a constructor from product

    public Product(int productId, String productName, double price, int stock) {
        this.productId=productId;
        this.productName=productName;
        this.price=price;
        this.stock=stock;
    }

    // Getter for get the productId
    public int getProductId() {
        return this.productId;
    }

    // Getter for stock
    public int getStock() {
        return this.stock;
    }

    // Getter for get the productId
    public Double getPrice() {
        return this.price;
    }

    // Getter for get the productId
    public String getProductName() {
        return this.productName;
    }

    // Setter for stock
    public void setStock(int stock) {
        this.stock = stock;
    }

// Create a method to get details of the product

    public String getDetails() {
        return "Product ID :" + productId+", "+ "Product Name :"+ productName+", "+ "Price :" + price+", "+ "Stock :" + stock;
    }
}
