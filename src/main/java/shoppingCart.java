import java.util.ArrayList;
import java.util.List;

public class shoppingCart {
    private List<Product> products;

    public shoppingCart() {
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addItem(Product product) {
        products.add(product);
    }

    public void removeItem(int productId) {
        products.removeIf(product -> product.getProductId() == productId);
    }

    public double totalCost() {
        double totalCost = 0;
        for (Product product : products) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }

    @Override
    public String toString() {
        StringBuilder cartDetails = new StringBuilder();
        for (Product product : products) {
            cartDetails.append(product.getDetails()).append("\n");
        }
        return cartDetails.toString();
    }
}
