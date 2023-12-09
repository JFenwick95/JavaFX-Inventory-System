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
 * Supplied class ModifyProductController.java
 */

/**
 *This is the ModifyProductController class and controls the ModifyProduct FXML file
 * @author Jeff Fenwick
 */
public class ModifyProductController implements Initializable {

    /**
     * Creates two temporary lists that are used to store the lists of available parts
     */
    private static ObservableList<Part> tempAllPartsList = FXCollections.observableArrayList();

    private static ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    /**
     * Adds a product object to store the information from the inventory scene
     */
    private Product selectedProduct;

    /**
     * The full list of FXML IDs in the ModifyProduct FXML file
     */
    @FXML
    private TextField ModifyProductIDField;

    @FXML
    private Label AddPartPartAddedLabel;

    @FXML
    private TextField AddPartsSearch;

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
    private Button ModifyPartAssociatedButton;

    @FXML
    private Label ModifyProductAnnounceLabel;

    @FXML
    private Button ModifyProductCancelButton;

    @FXML
    private TextField ModifyProductInvField;

    @FXML
    private TextField ModifyProductMaxField;

    @FXML
    private TextField ModifyProductMinField;

    @FXML
    private TextField ModifyProductNameField;

    @FXML
    private TextField ModifyProductPriceField;

    @FXML
    private Button ModifyProductSaveButton;

    @FXML
    private Button ModifyRemovePartAssociatedButton;

    /**
     * Moves parts from the upper list of parts to the lower list
     * @param event
     */
    @FXML
    void ModifyPartAssociatedButtonPress(ActionEvent event) {
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
    void ModifyProductCancelButtonPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Inventory.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * <b>Save button</b>
     * Saves the modified product back to the product list.
     * @param event
     * @throws IOException
     */
    @FXML
    void ModifyProductSaveButtonPress(ActionEvent event) throws IOException {
        int pdtID = 0;
        String name = null;
        Double priceCost = null;
        int inv = 0;
        int min = 0;
        int max = 0;

        try {
            pdtID = Integer.parseInt(ModifyProductIDField.getText());
            name = ModifyProductNameField.getText();
            priceCost = Double.parseDouble(ModifyProductPriceField.getText());
            inv = Integer.parseInt(ModifyProductInvField.getText());
            min = Integer.parseInt(ModifyProductMinField.getText());
            max = Integer.parseInt(ModifyProductMaxField.getText());
        }
        catch (NumberFormatException e) {
            MyAlarms.formatAlert.showAndWait();
            return;
        }

        if (!(min <= inv && inv <= max) || min > max) {
            MyAlarms.minMaxAlert.showAndWait();
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

        Inventory.addProduct(new Product(pdtID, name, priceCost, inv, min, max));

        for(Product pdt : getAllProducts()) {
            if(pdt.getId() == pdtID) {
                for(Part ascPdt : associatedPartList) {
                    pdt.addAssociatedPart(ascPdt);
                }
            }
        }

        allProducts.remove(selectedProduct);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Inventory.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
    void ModifyRemovePartAssociatedButtonPress(ActionEvent event) {
        Part pt = AssociatedPartsTable.getSelectionModel().getSelectedItem();
        if (pt == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + pt.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.YES) {
            tempAllPartsList.add(pt);
            associatedPartList.remove(pt);
            AllPartsTable.setItems(tempAllPartsList);
        }
        return;
    }

    /**
     * <b>Initial load of selected data</b>
     * Grabs data from the inventory scene and the selected product.
     * @param pt
     */
    public void initData(Product pt) {
        selectedProduct = pt;

        tempAllPartsList.clear();
        associatedPartList.clear();

        ObservableList<Part> selectedPartList = selectedProduct.getAssociatedParts();

        for(Part selPt : selectedPartList) {
            associatedPartList.add(selPt);
        }

        for(Part allPt : allParts) {
            if (!tempAllPartsList.contains(allPt) && !associatedPartList.contains(allPt)) {
                tempAllPartsList.add(allPt);
            }
        }

        ModifyProductIDField.setText(String.valueOf(selectedProduct.getId()));
        ModifyProductNameField.setText(String.valueOf(selectedProduct.getName()));
        ModifyProductInvField.setText(String.valueOf(selectedProduct.getStock()));
        ModifyProductPriceField.setText(String.valueOf(selectedProduct.getPrice()));
        ModifyProductMaxField.setText(String.valueOf(selectedProduct.getMax()));
        ModifyProductMinField.setText(String.valueOf(selectedProduct.getMin()));
    }

    /**
     * Initial table fill
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        Alert nothingAlert = new Alert(Alert.AlertType.ERROR,
                "Part not found", ButtonType.OK);

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
                    nothingAlert.showAndWait();
                    AddPartsSearch.setText("");
                    return;
                }
            }
            catch (NumberFormatException e) {
                nothingAlert.showAndWait();
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
}
