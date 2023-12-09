package Controllers;

import Alarms.MyAlarms;
import Inventory.Inventory;
import Parts.Part;
import Products.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import task1.qkm2task1.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Inventory.Inventory.*;

/**
 * Supplied class AddProductController.java
 */

/**
 *This is the AddProductController class and controls the AddProduct FXML file
 * @author Jeff Fenwick
 */
public class AddProductController implements Initializable {

    /**
     * Creates two temporary lists that are used to store the lists of available parts
     */
    private static ObservableList<Part> tempAllPartsList = FXCollections.observableArrayList();

    private static ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    /**
     * The full list of FXML IDs in the AddProductController FXML file
     */
    @FXML
    private Label AddProductAnnounceLabel;

    @FXML
    private Button AddPartAssociatedButton;

    @FXML
    private TextField AddPartsSearch;

    @FXML
    private Label AddPartPartAddedLabel;

    @FXML
    private Button AddProductCancelButton;

    @FXML
    private TextField AddProductInvField;

    @FXML
    private TextField AddProductMaxField;

    @FXML
    private TextField AddProductMinField;

    @FXML
    private TextField AddProductNameField;

    @FXML
    private TextField AddProductPriceField;

    @FXML
    private Button AddProductSaveButton;

    @FXML
    private TableColumn<Part, Integer> AllPartIdColumn;

    @FXML
    private TableColumn<Part, Integer> AllPartInvColumn;

    @FXML
    private TableColumn<Part, String> AllPartNameColumn;

    @FXML
    private TableColumn<Part, Double> AllPartPriceColumn;

    @FXML
    private TableView<Part> AllPartsTable;

    @FXML
    private TableColumn<Part, Integer> AssociatedPartIdColumn;

    @FXML
    private TableColumn<Part, Integer> AssociatedPartInvColumn;

    @FXML
    private TableColumn<Part, String> AssociatedPartNameColumn;

    @FXML
    private TableColumn<Part, Double> AssociatedPartPriceColumn;

    @FXML
    private TableView<Part> AssociatedPartsTable;

    @FXML
    private Button RemovePartAssociatedButton;

    /**
     * Moves parts from the top table to the bottom table
     * @param event
     */
    @FXML
    void AddPartAssociatedButtonPress(ActionEvent event) {
        Part pt = AllPartsTable.getSelectionModel().getSelectedItem();
        if(pt == null) {
            return;
        }
        associatedPartList.add(pt);
        tempAllPartsList.remove(pt);
        AllPartsTable.setItems(tempAllPartsList);
    }

    /**
     * Cancel button. Returns to the inventory scene
     * @param event
     * @throws IOException
     */
    @FXML
    void AddProductCancelButtonPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Inventory.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * <b>Save button</b>
     * Saves the new product to the product list.
     * @param event
     * @throws IOException
     */
    @FXML
    void AddProductSaveButtonPress(ActionEvent event) throws IOException {

        int pdtID = 0;
        String name = null;
        Double priceCost = null;
        int inv = 0;
        int min = 0;
        int max = 0;

        try {
            name = AddProductNameField.getText();
            priceCost = Double.parseDouble(AddProductPriceField.getText());
            inv = Integer.parseInt(AddProductInvField.getText());
            min = Integer.parseInt(AddProductMinField.getText());
            max = Integer.parseInt(AddProductMaxField.getText());
        }
        catch (NumberFormatException e) {
            MyAlarms.formatAlert.showAndWait();
            return;
        }

        if (!(min <= inv && inv <= max) || min > max) {
            MyAlarms.formatAlert.showAndWait();
            return;
        }

        Double totalCostSelectedProducts = 0.0;
        for(Part pt : associatedPartList) {
            totalCostSelectedProducts = totalCostSelectedProducts + pt.getPrice();
        }

        if(priceCost < totalCostSelectedProducts) {
            MyAlarms.priceAlert.showAndWait();
            return;
        }

        pdtID = generateProductID();

        Inventory.addProduct(new Product(pdtID, name, priceCost, inv, min, max));

        for(Product pdt : allProducts) {
            if(pdt.getId() == pdtID) {
                for(Part pt : associatedPartList) {
                    pdt.addAssociatedPart(pt);
                }
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Inventory.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Moves parts from the lower list to the upper list
     * @param event
     */
    @FXML
    void RemovePartAssociatedButtonPress(ActionEvent event) {
        Part pt = AssociatedPartsTable.getSelectionModel().getSelectedItem();
        if(pt == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + pt.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        tempAllPartsList.add(pt);
        associatedPartList.remove(pt);
        AllPartsTable.setItems(tempAllPartsList);
    }

    /**
     * Initial table fill
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        associatedPartList.clear();

        for(Part pt : allParts) {
            if(!tempAllPartsList.contains(pt))
            tempAllPartsList.add(pt);
        }

        AllPartsTable.setItems(tempAllPartsList);
        AssociatedPartsTable.setItems(associatedPartList);

        AllPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AllPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AllPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AllPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        AssociatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * The search bar actions
     * @param event
     */
    @FXML
    void AddPartsSearchButtonPress(ActionEvent event) {
        if (tempAllPartsList.isEmpty()) {
            return;
        }

        String q =AddPartsSearch.getText();

        ObservableList<Part> searchParts = lookupPartName(q);

        if (searchParts.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Part pt = lookupPartId(id);
                if(pt != null) {
                    searchParts.add(pt);
                }
                else {
                    MyAlarms.nothingAlert.showAndWait();
                    AddPartsSearch.setText("");
                    return;
                }
            }
            catch (NumberFormatException e) {
                MyAlarms.nothingAlert.showAndWait();
                AddPartsSearch.setText("");
                return;
            }
        }

        for (Part pt : associatedPartList) {
            if(searchParts.contains(pt)) {
                searchParts.remove(pt);
            }
        }

        AllPartsTable.setItems(searchParts);
        AddPartsSearch.setText("");
    }


    /**
     * Generates a unique product ID by finding the ID with the highest value and adding 1
     * @return a unique product id
     */
    private int generateProductID() {
        int maxProductNumber = 0;
        int generatedProductId = 0;
        for(Product pdt : allProducts) {
            if(pdt.getId() > maxProductNumber) {
                maxProductNumber = pdt.getId();
            }
        }
        generatedProductId = maxProductNumber + 1;
        return generatedProductId;
    }
}
