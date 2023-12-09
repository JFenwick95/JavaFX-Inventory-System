package Alarms;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * class AddProductController.java
 */

/**
 * This is the MyAlarms class and contains all of the alerts that are used except for the delete confirmation
 * @author Jeff Fenwick
 */
public class MyAlarms {

    /**
     * The alert when a part is not found in a search bar.
     */
    public static Alert nothingAlert = new Alert(Alert.AlertType.ERROR,
            "Part not found", ButtonType.OK);

    /**
     * The alert when a product is not found in a search bar.
     */
    public static Alert nothingAlertProduct = new Alert(Alert.AlertType.ERROR,
            "Product not found", ButtonType.OK);

    /**
     * The alert when attempting to delete an item when nothing is selected.
     */
    public static Alert nothingDeletedAlert = new Alert(Alert.AlertType.ERROR,
            "Nothing Selected", ButtonType.OK);

    /**
     * The alert when attempting to delete a part that is associated with a product.
     */
    public static Alert ascPartAlert = new Alert(Alert.AlertType.ERROR,
            "Part cannot be deleted. Part is associated to a product.", ButtonType.OK);

    /**
     * The alert when there is a number format error.
     */
    public static Alert formatAlert = new Alert(Alert.AlertType.ERROR,
            "Number Format Alert", ButtonType.OK);

    /**
     * The alert when the min, inv, and max values rules are broken.
     */
    public static Alert minMaxAlert = new Alert(Alert.AlertType.ERROR,
            "Min <= Inv <= Max", ButtonType.OK);

    /**
     * The alert when the price of the product is not more than the sum of the parts.
     */
    public static Alert priceAlert = new Alert(Alert.AlertType.ERROR,
            "Price must be more than total value of parts", ButtonType.OK);
}

