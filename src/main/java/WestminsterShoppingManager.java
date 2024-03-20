/// Main class of the Shopping Manager

import javax.swing.*;
import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> products = new ArrayList<>();
    private final int MAX_PRODUCTS = 50;
    private static final String DATA_FILE = "product_data.txt";

    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("WESTMINSTER Shopping Manager Menu");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list of products");
            System.out.println("4. Save products to file");
            System.out.println("5. Exit from the Application");
            System.out.println("Enter Your Choice");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addProduct(scanner);
                case 2 -> deleteProduct(scanner);
                case 3 -> printProductsList();
                case 4 -> saveProductsListToFile();
                case 5 -> System.out.println("Exit");
                default -> System.out.println("Invalid option. Please try again");
            }

        } while (choice != 5);

        scanner.close();
    }

    // Load products from file method
    @Override
    public void loadProductsFromFile() {
        try (Scanner fileScanner = new Scanner(new File(DATA_FILE))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.startsWith("Electronics")) {
                    Product electronicsProduct = parseProduct(line, "Electronics");
                    if (electronicsProduct != null) {
                        products.add(electronicsProduct);
                    }
                } else if (line.startsWith("Clothing")) {
                    Product clothingProduct = parseProduct(line, "Clothing");
                    if (clothingProduct != null) {
                        products.add(clothingProduct);
                    }
                }
            }
            System.out.println("Products loaded from the file");
        } catch (IOException e) {
            System.out.println("An error occurred while loading data from the file: " + e.getMessage());
        }
    }

    // Helper method to parse a product from a string
    private Product parseProduct(String line, String productType) {
        String[] tokens = line.split(", ");
        if (tokens.length < 5) {
            System.out.println("Invalid data format: " + line);
            return null;
        }

        try {
            int productId = extractValue(tokens[0]);
            String productName = extractString(tokens[1]);
            double price = extractValueDouble(tokens[2]);
            int stock = extractValue(tokens[3]);

            if (productType.equals("Electronics")) {
                int warranty = extractValue(tokens[4]);
                String brandName = extractString(tokens[5]);
                return new Electronics(productId, productName, price, stock, warranty, brandName);
            } else if (productType.equals("Clothing")) {
                String size = extractString(tokens[4]);
                String color = extractString(tokens[5]);
                return new Clothing(productId, productName, price, stock, size, color);
            } else {
                System.out.println("Unknown product type: " + productType);
                return null;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error parsing line: " + line);
            return null;
        }
    }


    private int extractValue(String token) {
        return Integer.parseInt(token.split(":")[1].trim());
    }

    private String extractString(String token) {
        return token.split(":")[1].trim();
    }

    private double extractValueDouble(String token) {
        return Double.parseDouble(token.split(":")[1].trim());
    }

    // Add a new product method
    @Override
    public void addProduct(Scanner scanner) {
        if (products.size() >= MAX_PRODUCTS) {
            System.out.println("Maximum product limit reached.");
            return;
        }

        int type;
        System.out.println("Enter product type - (1 for Electronic / 2 for Clothing): ");
        while (true) {
            try {
                type = scanner.nextInt();
                if (type == 1 || type == 2) {
                    break;
                } else {
                    System.out.println("Invalid product type. Please enter 1 for Electronic or 2 for Clothing.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for product type. Please enter 1 for Electronic or 2 for Clothing.");
                scanner.next();  // Consume the invalid input
            }
        }


        int id;
        System.out.println("Enter product ID: ");
        while (true) {
            try {
                id = scanner.nextInt();
                if (isProductIdUsed(id)) {
                    System.out.println("Product ID is already used. Do you want to update the stock? (y/n)");
                    scanner.nextLine(); // Consume the newline character
                    String response = scanner.nextLine().trim().toLowerCase();
                    if (response.equals("y")) {
                        updateStock(id, scanner);
                        return;
                    } else {
                        System.out.println("Please enter a different ID.");
                    }
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for product ID. Please enter a valid integer.");
                scanner.next();  // Consume the invalid input
            }
        }

        scanner.nextLine();

        String productName = "";
        System.out.println("Enter product name: ");
        while (true) {
            try {
                productName = scanner.nextLine();
                Integer.parseInt(productName);  // Attempt to convert to Integer
                System.out.println("Invalid input for product name. Please enter a valid string.");
            } catch (NumberFormatException e) {
                // The input is a valid string
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for product name. Please enter a valid string.");
                scanner.next();  // Consume the invalid input
            }
        }

        double price;
        System.out.println("Enter price: ");
        while (true) {
            try {
                price = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for price. Please enter a valid numeric value.");
                scanner.next();  // Consume the invalid input
            }
        }

        int stock;
        System.out.println("Enter number of items in stock: ");
        while (true) {
            try {
                stock = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for stock. Please enter a valid integer.");
                scanner.next();  // Consume the invalid input
            }
        }

        scanner.nextLine();

        if (type == 1) { // Electronics
            int warranty;
            System.out.println("Enter warranty period (Months): ");
            while (true) {
                try {
                    warranty = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for warranty. Please enter a valid numeric value.");
                    scanner.next();  // Consume the invalid input
                }
            }

            scanner.nextLine();

            String brandName = "";
            System.out.println("Enter product brand: ");
            while (true) {
                try {
                    brandName = scanner.nextLine();
                    Integer.parseInt(brandName);  // Attempt to convert to Integer
                    System.out.println("Invalid input for product brand. Please enter a valid string.");
                } catch (NumberFormatException e) {
                    // The input is a valid string
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input for product name. Please enter a valid string.");
                    scanner.next();  // Consume the invalid input
                }
            }

            // Create Electronic product based on the provided inputs...
            products.add(new Electronics(id, productName, price, stock, warranty, brandName));

        } else if (type == 2) { // Clothing
            String size = "";
            System.out.println("Enter product size: ");
            while (true) {
                try {
                    size = scanner.nextLine();
                    Integer.parseInt(size);  // Attempt to convert to Integer
                    System.out.println("Invalid input. Please enter a valid string.");
                } catch (NumberFormatException e) {
                    // The input is a valid string
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid string.");
                    scanner.next();  // Consume the invalid input
                }
            }

            String color = "";
            System.out.println("Enter product color: ");
            while (true) {
                try {
                    color = scanner.nextLine();
                    Integer.parseInt(color);  // Attempt to convert to Integer
                    System.out.println("Invalid input. Please enter a valid string.");
                } catch (NumberFormatException e) {
                    // The input is a valid string
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid string.");
                    scanner.next();  // Consume the invalid input
                }
            }
            // Create Clothing product based on the provided inputs...
            products.add(new Clothing(id, productName, price, stock, size, color));
        } else {
            System.out.println("Invalid product type. Please enter 1 for Electronic or 2 for Clothing.");
        }

        System.out.println("Product added successfully");
    }

    // Helper method to update the stock of an existing product
    private void updateStock(int productId, Scanner scanner) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                int newStock;
                System.out.println("Enter the new stock for the product (current stock: " + product.getStock() + "): ");
                while (true) {
                    try {
                        newStock = scanner.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for stock. Please enter a valid integer.");
                        scanner.next();  // Consume the invalid input
                    }
                }
                product.setStock(newStock);
                System.out.println("Stock updated successfully");
                return;
            }
        }
    }

    // Helper method to check if a product ID is already used
    private boolean isProductIdUsed(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return true;
            }
        }
        return false;
    }

    // Delete product method
    @Override
    public void deleteProduct(Scanner scanner) {
        System.out.println("Enter product ID to delete the product: ");
        int id;
        while (true) {
            try {
                id = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for product ID. Please enter a valid integer.");
                scanner.next();  // Consume the invalid input
            }
        }

        Product toRemove = null;
        for (Product product : products) {
            if (product.getProductId() == id) {
                toRemove = product;
                break;
            }
        }

        if (toRemove != null) {
            products.remove(toRemove);
            System.out.println("Deleted Product: " + toRemove.getDetails());
        } else {
            System.out.println("Product not found");
        }

        System.out.println("Total number of products: " + products.size());
    }


    // Print the list of products method
    @Override
    public void printProductsList(){
        Collections.sort(products, Comparator.comparing(Product::getProductId));
        for (Product product:products){
            System.out.println(product.getDetails());
        }
    }

    // Save the product data into a file method
    @Override
    public void saveProductsListToFile() {
        try (FileWriter writer = new FileWriter(DATA_FILE); BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (Product product : products) {
                bufferedWriter.write(product.getDetails());
                bufferedWriter.newLine();
            }
            System.out.println("Products saved to the file");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null; // If no product with the specified ID is found
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    public static void main(String[] args) {
        WestminsterShoppingManager shop = new WestminsterShoppingManager();

        Scanner scanner = new Scanner(System.in);
        shop.loadProductsFromFile();
        System.out.println("Are you a manager or a customer? (Enter 'manager' or 'customer'): ");
        String userType = scanner.nextLine().toLowerCase();

        if ("manager".equals(userType)) {
            shop.displayMenu();
        } else if ("customer".equals(userType)) {
//           openCustomerUI();
        } else {
            System.out.println("Invalid user type. Exiting the application.");
        }

        scanner.close();
    }

    //UI Code
    private static void openCustomerUI() {
        WestminsterShoppingManager shop = new WestminsterShoppingManager();
        SwingUtilities.invokeLater(() -> {
            new userGUI(shop).setVisible(true);
        });
    }
}



