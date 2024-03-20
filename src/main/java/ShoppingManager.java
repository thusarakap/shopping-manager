import java.util.Scanner;
import java.util.List;

public interface ShoppingManager {

    void displayMenu();

    void addProduct(Scanner scanner);

    void deleteProduct(Scanner scanner);

    List<Product> getProducts();  // Changed from static

    void printProductsList();

    void saveProductsListToFile();

    void loadProductsFromFile();
}






