package util;

import javafx.scene.control.Alert;

/**
 * Generate an alert with custom message
 */
public abstract class AlertMessage {

    public static void alert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATTENTION !");
        alert.setHeaderText(message);
        alert.setContentText("");

        alert.showAndWait();
    }
}
