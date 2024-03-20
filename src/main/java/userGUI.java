import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class userGUI extends JFrame {
    private List<Product> products;
    private WestminsterShoppingManager shop;
    private JComboBox<String> productTypeComboBox;
    private JTable productsTable;
    private DefaultTableModel tableModel;

    public userGUI(WestminsterShoppingManager shop) {
        this.products = shop.getProducts();

        setTitle("Product Display");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
        createLayout();
        loadProductsData(); // Load initial product data into the table
    }

    private void initializeComponents() {
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterProducts();
            }
        });

        tableModel = new DefaultTableModel();
        productsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(productsTable);

        JButton sortButton = new JButton("Sort Alphabetically");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortProductsAlphabetically();
            }
        });

        add(productTypeComboBox, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(sortButton, BorderLayout.SOUTH);
    }

    private void createLayout() {
        setLayout(new BorderLayout());
    }

    private void loadProductsData() {
        List<Product> allProducts = shop.getProducts();
        updateTable(allProducts);
    }

    private void filterProducts() {
        String selectedType = (String) productTypeComboBox.getSelectedItem();
        List<Product> filteredProducts;

        if ("Electronics".equals(selectedType)) {
            filteredProducts = filterElectronics();
        } else if ("Clothes".equals(selectedType)) {
            filteredProducts = filterClothes();
        } else {
            filteredProducts = shop.getProducts();
        }

        updateTable(filteredProducts);
    }

    private List<Product> filterElectronics() {
        List<Product> allProducts = shop.getProducts();
        List<Product> electronics = new ArrayList<>();

        for (Product product : allProducts) {
            if (product instanceof Electronics) {
                electronics.add(product);
            }
        }

        return electronics;
    }

    private List<Product> filterClothes() {
        List<Product> allProducts = shop.getProducts();
        List<Product> clothes = new ArrayList<>();

        for (Product product : allProducts) {
            if (product instanceof Clothing) {
                clothes.add(product);
            }
        }

        return clothes;
    }

    private void updateTable(List<Product> products) {
        clearTable();
        for (Product product : products) {
            tableModel.addRow(productToRowData(product));
        }
    }

    private Object[] productToRowData(Product product) {
        return new Object[]{product.getProductId(), product.getProductName(), product.getPrice(), product.getStock()};
    }

    private void clearTable() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Name", "Price", "Stock"});
    }

    private void sortProductsAlphabetically() {
        List<Product> products = shop.getProducts();
        Collections.sort(products, (p1, p2) -> p1.getProductName().compareToIgnoreCase(p2.getProductName()));
        updateTable(products);
    }

    public static void main(String[] args) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        SwingUtilities.invokeLater(() -> {
            new userGUI(manager).setVisible(true);
        });
    }
}
