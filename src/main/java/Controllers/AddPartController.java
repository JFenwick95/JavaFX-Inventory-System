package Controllers;

import Alarms.MyAlarms;
import Inventory.Inventory;
import Parts.InHouse;
import Parts.Outsourced;
import Parts.Part;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import task1.qkm2task1.Main;
import javafx.scene.control.Label;


import java.io.IOException;

import static Inventory.Inventory.allParts;

/**
 * Supplied class AddPartController.java
 */

/**
 *This is the AddPartController class and controls the AddPart FXML file
 * @author Jeff Fenwick
 */
public class AddPartController {

    /**
     * The full list of FXML IDs in the ModifyPart FXML file
     */
    @FXML
    private Button AddPartCancelButton;

    @FXML
    private Rectangle AddPartInHouseBox;

    @FXML
    private Label AddPartInHouseIDLabel;

    @FXML
    private Label AddPartInHouseTitle;

    @FXML
    private Label AddPartInHouseTitleLabel;

    @FXML
    private Label AddPartPartAddedLabel;

    @FXML
    private Label AddPartInHouseMachineIDLabel;

    @FXML
    private Button AddPartSaveButton;

    @FXML
    private RadioButton InHouseRadioButton;

    @FXML
    private RadioButton OutsourcedRadioButton;

    @FXML
    private ToggleGroup AddPartInHouseToggleGroup;

    @FXML
    private TextField AddPartInvField;

    @FXML
    private TextField AddPartMaxField;

    @FXML
    private TextField AddPartMinField;

    @FXML
    private TextField AddPartNameField;

    @FXML
    private TextField AddPartOtherField;

    @FXML
    private TextField AddPartPriceCostField;

    /**
     * <b>Save button</b>
     * Saves the new Part to the part list.
     * @param event
     * @throws IOException
     */
    @FXML
    void AddPartSaveButtonPress(ActionEvent event) throws IOException {
        String name = null;
        Double priceCost = null;
        int inv = 0;
        int min = 0;
        int max = 0;
        int partMachineId = 0;
        String partCompanyName = null;

        try {
            name = AddPartNameField.getText();
            priceCost = Double.parseDouble(AddPartPriceCostField.getText());
            inv = Integer.parseInt(AddPartInvField.getText());
            min = Integer.parseInt(AddPartMinField.getText());
            max = Integer.parseInt(AddPartMaxField.getText());
            if(InHouseRadioButton.isSelected()) {
                partMachineId = Integer.parseInt(AddPartOtherField.getText());
            }
            if (OutsourcedRadioButton.isSelected()) {
                partCompanyName = (AddPartOtherField.getText());
            }
        }
        catch (NumberFormatException e) {
            MyAlarms.formatAlert.showAndWait();
            return;
        }

        if (!(min <= inv && inv <= max) || min > max) {
            MyAlarms.minMaxAlert.showAndWait();
            return;
        }

        if(InHouseRadioButton.isSelected()) {
            Inventory.addPart(new InHouse(generatePartID(), name, priceCost, inv,
                    min, max, partMachineId));
        }

        if(OutsourcedRadioButton.isSelected()) {
            Inventory.addPart(new Outsourced(generatePartID(), name, priceCost, inv,
                    min, max, partCompanyName));
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Inventory.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cancel button. Returns to the inventory scene
     * @param event
     * @throws IOException
     */
    public void AddPartCancelButtonPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Inventory.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Outsourced radio button action. Sets the text on the bottom label to Company Name
     * @param event
     */
    @FXML
    void AddPartOutsourcedRadioButtonPress(ActionEvent event) throws  IOException {
        AddPartInHouseMachineIDLabel.setText("Company Name");

    }

    /**
     * In House radio button action. Sets the text on the bottom label to Machine ID
     * @param event
     */
    @FXML
    void AddPartInHouseRadioButtonPress(ActionEvent event) {
        AddPartInHouseMachineIDLabel.setText("Machine ID");

    }

    /**
     * Generates a unique part ID by finding the ID with the highest value and adding 1
     * @return a unique part id
     */
    private int generatePartID() {
        int maxPartNumber = 0;
        int generatedPartId = 0;
        for(Part pt : allParts) {
            if(pt.getId() > maxPartNumber) {
                maxPartNumber = pt.getId();
            }
        }
        generatedPartId = maxPartNumber + 1;
        return generatedPartId;
    }
}
