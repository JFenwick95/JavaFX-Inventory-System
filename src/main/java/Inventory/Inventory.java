package Inventory;



import Alarms.MyAlarms;
import Parts.InHouse;
import Parts.Part;
import Products.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * class Inventory.java
 */

/**
 *<b>This is the Inventory class</b>
 *<p>Performs operations on products and parts</p>
 * @author Jeff Fenwick
 */
public class Inventory {

    /**
     * Creates an observable list called allParts
     */
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();


    /**
     * Creates an observable list called allProducts
     */
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * @param newPart adds a new part to allParts
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /**
     * @param newProduct adds a new product to allProducts
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }


    /**
     * Search parts by part number
     *
     * @param partId takes an int as an ID
     * @return the part that matches the ID
     */
    public static Part lookupPartId(int partId) {
        for (Part pt : allParts) {
            if (pt.getId() == partId) {
                return pt;
            }
        }
        return null;
    }


    /**
     * Search products by ID
     *
     * @param productId takes an int as an ID
     * @return the product that matches the ID
     */
    public static Product lookupProductId(int productId) {
        for (Product pdt : allProducts) {
            if (pdt.getId() == productId) {
                return pdt;
            }
        }
        return null;
    }


    /**
     * Search parts by part partial name
     *
     * @param partName takes a string as partial name
     * @return list of parts that match the partial name
     */
    public static ObservableList<Part> lookupPartName(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        for (Part pt : allParts) {
            if (pt.getName().contains(partName)) {
                namedParts.add(pt);
            }
        }
        return namedParts;
    }


    /**
     * Search products by partial product name
     *
     * @param productName takes a string as a partial name
     * @return a list of products that match the search
     */
    public static ObservableList<Product> lookupProductName(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        for (Product pdt : allProducts) {
            if (pdt.getName().contains(productName)) {
                namedProducts.add(pdt);
            }
        }
        return namedProducts;
    }


    /**
     * Updates parts. This is unused as I have a different system of modifying parts
     */
    public static void updatePart(int index, Part selectedPart) {
    }


    /**
     *Updates products. This is unused as I have a different system of modifying products
     */
    public void updateProduct(int index, Product newProduct) {

    }


    /**
     * Delete part method that returns a boolean value to confirm or deny deletion of a part
     *
     * @param selectedPart takes the selection from the parts table
     * @return true confirms part for deletion
     * @return false denies the part for deletion
     */
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart == null) {
            MyAlarms.nothingDeletedAlert.showAndWait();
            return false;
        }

        for (Product pdt : getAllProducts()) {
            if (pdt.getAssociatedParts().contains(selectedPart)) {
                MyAlarms.ascPartAlert.showAndWait();
                return false;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + selectedPart.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                return true;
            }
        return false;
    }


    /**
     * Delete product method that returns a boolean value to confirm or deny deletion of a product
     *
     * @param selectedProduct takes the selection from the parts table
     * @return true confirms product for deletion
     * @return false denies the product for deletion
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (selectedProduct == null) {
            MyAlarms.nothingDeletedAlert.showAndWait();
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + selectedProduct.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return true;
        }
        return false;
    }


    /**
     * @return the full list of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /**
     * @return the full list of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}



