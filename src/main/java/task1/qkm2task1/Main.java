/**This is the package that contains the Main class
 * @author Jeff Fenwick
 */
package task1.qkm2task1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Supplied class Main.java
 */

/**
 *This is the main class which sets the first scene and runs the application.
 * <p><b>FUTURE ENHANCEMENT</b>
 * A way of extending the functionality of this program would be a way of tracking stock of parts while enabling
 * adding of additional quantities of the same part to different products. While adding multiple of the same parts to a
 * product, there could be a tracker that alerts you if you have used up your stock of that part. The type of parts
 * could also go into more detail as well as going one level further and having parts be part of subassemblies which
 * are part of products.</p>
 *
 * <p>
 *     The RUNTIME ERROR comment is located in the Inventory Controller class description.
 * </p>
 *
 * @author Jeff Fenwick
 */
public class Main extends Application {

    /**
     * This sets the first scene, which is the inventory scene
     * @param stage sets the initial stage on the Inventory scene
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Inventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method. The first method that gets called.
     * @param args Unused
     */
    public static void main(String[] args) {
        launch();
    }
}