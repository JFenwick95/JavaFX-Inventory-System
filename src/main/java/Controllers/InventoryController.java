package Controllers;

import Alarms.MyAlarms;
import Inventory.Inventory;
import Parts.InHouse;
import Parts.Outsourced;
import Parts.Part;
import Products.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import task1.qkm2task1.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Inventory.Inventory.*;

/**
 * Supplied class InventoryController.java
 */

/**
 *This is the InventoryController class and controls the Inventory FXML file
 *
 * <p><b>RUNTIME ERROR</b>
 * I had a many errors when trying to figure out how the search bars worked. One memorable one was getting an error
 * every time I entered non-matching text. I could enter non-matching ID number and deal with the format error just
 * fine, but with text, the program wasn't leading to the error alert I had set up. I had to set up a try/catch
 * for number formatting to get the correct alert to be shown. This was covered in the webinars, but I had forgotten
 * and spent a long time trying to figure it out again.</p>
 *
 * @author Jeff Fenwick
 */
public class InventoryController implements Initializable {

    Inventory inventoryInstance;

    /**
     * The full list of FXML IDs in the Inventory FXML file
     */
    @FXML
    private Label InventoryTitle;

    @FXML
    private Button PartAddButton;

    @FXML
    private Rectangle PartBox;

    @FXML
    private Button PartDeleteButton;

    @FXML
    private TableColumn<Part, Integer> PartIDColumn;

    @FXML
    private TableColumn<Part, Integer> PartLevelColumn;

    @FXML
    private Button PartModifyButton;

    @FXML
    private TableColumn<Part, String> PartNameColumn;

    @FXML
    private TableView<Part> PartTable;

    @FXML
    private TableColumn<Part, Double> PartsPriceColumn;

    @FXML
    private TextField PartsSearch;

    @FXML
    private Label PartsTitle;

    @FXML
    private Button ProductAddButton;

    @FXML
    private Rectangle ProductBox;

    @FXML
    private Button ProductDeleteButton;

    @FXML
    private TableColumn<Product, Integer> ProductIdColumn;

    @FXML
    private TableColumn<Product, Integer> ProductLevelColumn;

    @FXML
    private Button ProductModifyButton;

    @FXML
    private TableColumn<Product, String> ProductNameColumn;

    @FXML
    private TableColumn<Product, Double> ProductPriceColumn;

    @FXML
    private TextField ProductSearch;

    @FXML
    private TableView<Product> ProductTable;

    @FXML
    private Label ProductTitle;

    @FXML
    private Button InventoryExitButton;

    /**
     * Allows the creation of test parts and products only one time
     */
    private static boolean firstTime = true;

    /**
     * Adds test parts and products their respective overall lists
     */
    private void addTestPartsAndProducts() {
        if (!firstTime) {
            return;
        }
        firstTime = false;

        addPart(new InHouse(1, "Brakes", 9.99, 10, 1, 20, 111));
        addPart(new Outsourced(2, "Wheel", 15.99, 7, 4, 28,
                "Viego"));
        addPart(new InHouse(3, "Crank", 99.99, 12, 6, 60, 113));
        addPart(new Outsourced(4, "Light", 54.99, 10, 2, 20,
                "Abraxas"));

        addProduct(new Product(101, "Infinity", 499.99, 4, 1, 10));
        addProduct(new Product(102, "Diamond", 299.99, 10, 5, 20));
        addProduct(new Product(103, "Sultan", 649.99, 3, 1, 10));
        addProduct(new Product(104, "Luxor", 999.99, 1, 1, 2));
    }

    /**
     * <b>The initial actions upon entering the inventory screen</b>
     *Creates and populates the tables while adding example parts and products.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTestPartsAndProducts();

        PartTable.setItems(Inventory.getAllParts());
        ProductTable.setItems(Inventory.getAllProducts());

        PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Action for pressing the Add button under the part table
     * @param actionEvent
     * @throws IOException
     */
    public void AddPartButtonPress(ActionEvent actionEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddPart.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
            stage.setTitle("Add Part");
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Action for pressing the Delete button under the part table
     * @param event
     */
    @FXML
    void DeletePartButtonPress(ActionEvent event) {
        Part selectedPart = PartTable.getSelectionModel().getSelectedItem();

        if(Inventory.deletePart(selectedPart) == true) {
            allParts.remove(selectedPart);
            PartTable.setItems(getAllParts());
            ProductTable.setItems(allProducts);
        }
        return;
    }

    /**
     * Action for pressing the Modify button under the part table
     * @param event
     * @throws IOException
     */
    @FXML
    void ModifyPartButtonPress(ActionEvent event) throws IOException {
        if(!PartTable.getSelectionModel().isEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModifyPart.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
            ModifyPartController controller = fxmlLoader.getController();
            controller.initData(PartTable.getSelectionModel().getSelectedItem());
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Action event for Part search text box enter press
     */
    @FXML
    void PartsSearchButtonPress(ActionEvent actionEvent) {
        if (Inventory.getAllParts().isEmpty()) {
            return;
        }

        String q =PartsSearch.getText();

        ObservableList<Part> parts = lookupPartName(q);
        if (parts.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Part pt = lookupPartId(id);
                if(pt != null) {
                    parts.add(pt);
                }
                else {
                    MyAlarms.nothingAlert.showAndWait();
                    PartsSearch.setText("");
                    ProductSearch.setText("");
                    return;
                }
            }
            catch (NumberFormatException e) {
                MyAlarms.nothingAlert.showAndWait();
                PartsSearch.setText("");
                ProductSearch.setText("");
                return;
            }

        }
        PartTable.setItems(parts);
        PartsSearch.setText("");
        ProductSearch.setText("");
    }

    /**
     * Action for pressing the Add button under the product table
     * @param event
     * @throws IOException
     */
    @FXML
    void AddProductButtonPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddProduct.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Action for pressing the Delete button under the product table
     * @param event
     */
    @FXML
    void DeleteProductButtonPress(ActionEvent event) {
        Product selectedProduct = ProductTable.getSelectionModel().getSelectedItem();

        if(Inventory.deleteProduct(selectedProduct) == true) {
            allProducts.remove(selectedProduct);
            PartTable.setItems(getAllParts());
            ProductTable.setItems(getAllProducts());
        }
        return;
    }

    /**
     * Action for pressing the Modify button under the product table
     * @param event
     * @throws IOException
     */
    @FXML
    void ModifyProductButtonPress(ActionEvent event) throws IOException {
        if (!ProductTable.getSelectionModel().isEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
            ModifyProductController controller = fxmlLoader.getController();
            controller.initData(ProductTable.getSelectionModel().getSelectedItem());
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }


    /**
     * Action event for Product search text box enter press
     */
    @FXML
    void ProductsSearchButtonPress(ActionEvent actionEvent) {
        if (Inventory.getAllProducts().isEmpty()) {
            return;
        }

        String q =ProductSearch.getText();

        ObservableList<Product> products = lookupProductName(q);

        if (products.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Product pdt = lookupProductId(id);
                if(pdt != null) {
                    products.add(pdt);
                }
                else {
                    MyAlarms.nothingAlertProduct.showAndWait();
                    PartsSearch.setText("");
                    ProductSearch.setText("");
                    return;
                }
            }
            catch (NumberFormatException e) {
                MyAlarms.nothingAlertProduct.showAndWait();
                PartsSearch.setText("");
                ProductSearch.setText("");
                return;
            }
        }

        ProductTable.setItems(products);
        PartsSearch.setText("");
        ProductSearch.setText("");
    }

    /**
     * Action for pressing the Exit button
     * @param event
     */
    @FXML
    void InventoryExitButtonPress(ActionEvent event) {
        Platform.exit();
    }
}
