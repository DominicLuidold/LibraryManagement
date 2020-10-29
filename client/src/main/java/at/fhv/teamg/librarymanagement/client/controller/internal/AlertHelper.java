package at.fhv.teamg.librarymanagement.client.controller.internal;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AlertHelper {

    private AlertHelper() {}

    /**
     * Simple helper class for helping to throw an Alert in the GUI.
     *
     * @author Valentin Goronjic
     */
    public static void showAlert(
        Alert.AlertType alertType,
        Window owner,
        String title,
        String message
    ) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}