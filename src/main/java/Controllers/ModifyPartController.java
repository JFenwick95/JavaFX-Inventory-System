package Controllers;

import Alarms.MyAlarms;
import Inventory.Inventory;
import Parts.InHouse;
import Parts.Outsourced;
import Parts.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import task1.qkm2task1.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Inventory.Inventory.*;

/**
 * class ModifyPartController.java
 */

/**
 *This is the ModifyPartController class and controls the ModifyPart FXML file
 * @author Jeff Fenwick
 */
public class ModifyPartController implements Initializable {

    /**
     * The full list of FXML IDs in the ModifyPart FXML file
     */

    @FXML
    private Label ModifyPartInHouseMachineIDLabel;

    @FXML
    private ToggleGroup ModifyPartInHouseToggleGroup;

    @FXML
    private Label ModifyPartPartModifiedLabel;

    @FXML
    private RadioButton ModifyInHouseRadioButton;

    @FXML
    private RadioButton ModifyOutsourcedRadioButton;

    @FXML
    private Button ModifyPartCancelButton;

    @FXML
    private Rectangle ModifyPartInHouseBox;

    @FXML
    private Label ModifyPartInHouseIDLabel;

    @FXML
    private Label ModifyPartInHouseTitle;

    @FXML
    private Label ModifyPartInHouseTitleLabel;

    @FXML
    private TextField ModifyPartIDField;

    @FXML
    private TextField ModifyPartInvField;

    @FXML
    private TextField ModifyPartMaxField;

    @FXML
    private TextField ModifyPartMinField;

    @FXML
    private TextField ModifyPartNameField;

    @FXML
    private TextField ModifyPartOtherField;

    @FXML
    private TextField ModifyPartPriceCostField;

    @FXML
    private Button ModifyPartSaveButton;

    private Part selectedPart;

    /**
     * Action for pressing the Cancel button in the ModifyPart scene
     * @param event
     * @throws IOException
     */
    @FXML
    void ModifyPartCancelButtonPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Inventory.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * In House radio button action. Sets the text on the bottom label to Machine ID
     * @param event
     */
    @FXML
    void ModifyPartInHouseRadioButtonPress(ActionEvent event) {
        ModifyPartInHouseMachineIDLabel.setText("Machine ID");
    }

    /**
     * Outsourced radio button action. Sets the text on the bottom label to Company Name
     * @param event
     */
    @FXML
    void ModifyPartOutsourcedRadioButtonPress(ActionEvent event) {
        ModifyPartInHouseMachineIDLabel.setText("Company Name");
    }

    /**
     * The save button in the ModifyPart scene.
     * Takes input from all text boxes, checks for errors, and adds a new part while deleting the old part.
     * @param event
     * @throws IOException
     */
    @FXML
    void ModifyPartSaveButtonPress(ActionEvent event) throws IOException {
        int partID;
        String partName;
        double partPrice;
        int partInv;
        int partMin;
        int partMax;
        int partMachineID = 0;
        String partCompanyName = null;

        try {
            partID = Integer.parseInt(ModifyPartIDField.getText());
            partName = ModifyPartNameField.getText();
            partPrice = Double.parseDouble(ModifyPartPriceCostField.getText());
            partInv = Integer.parseInt(ModifyPartInvField.getText());
            partMin = Integer.parseInt(ModifyPartMinField.getText());
            partMax = Integer.parseInt(ModifyPartMaxField.getText());

            if(ModifyInHouseRadioButton.isSelected()) {
                partMachineID = Integer.parseInt(ModifyPartOtherField.getText());
            }

            if (ModifyOutsourcedRadioButton.isSelected()) {
                partCompanyName = (ModifyPartOtherField.getText());
            }
        }
        catch (NumberFormatException e) {
            MyAlarms.formatAlert.showAndWait();
            return;
        }

        if (!(partMin <= partInv && partInv <= partMax) || partMin > partMax) {
            MyAlarms.minMaxAlert.showAndWait();
            return;
        }

        if(ModifyInHouseRadioButton.isSelected()) {
            addPart(new InHouse(partID, partName, partPrice, partInv, partMin, partMax, partMachineID));
        }

        if(ModifyOutsourcedRadioButton.isSelected()) {
            addPart(new Outsourced(partID, partName, partPrice, partInv, partMin, partMax, partCompanyName));
        }

        getAllParts().remove(selectedPart);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Inventory.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Takes information from the Inventory scene and passes it to the text fields in the ModifyPart scene
     * @param pt
     */
    public void initData(Part pt) {

        selectedPart = pt;

        ModifyPartIDField.setText(String.valueOf(selectedPart.getId()));
        ModifyPartNameField.setText(String.valueOf(selectedPart.getName()));
        ModifyPartInvField.setText(String.valueOf(selectedPart.getStock()));
        ModifyPartPriceCostField.setText(String.valueOf(selectedPart.getPrice()));
        ModifyPartMaxField.setText(String.valueOf(selectedPart.getMax()));
        ModifyPartMinField.setText(String.valueOf(selectedPart.getMin()));
        if (selectedPart.getClass() == Outsourced.class) {
            ModifyPartOtherField.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
            ModifyPartInHouseMachineIDLabel.setText("Company Name");
            ModifyOutsourcedRadioButton.setSelected(true);
            ModifyInHouseRadioButton.setSelected(false);
        }
        if (selectedPart.getClass() == InHouse.class) {
            ModifyPartOtherField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
    }

    /**
     * Actions on initializing the scene. Not used.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
