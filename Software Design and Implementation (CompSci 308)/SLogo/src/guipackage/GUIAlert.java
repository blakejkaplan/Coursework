package guipackage;

import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Alert pop-ups for error handling.
 */
public class GUIAlert {
    private static final String ERROR_RESOURCE = "guipackage/Errors";
    private ResourceBundle myErrorResources;

    public GUIAlert() {
        myErrorResources = ResourceBundle.getBundle(ERROR_RESOURCE);
    }
    
    /**
     * Displays the Alert based on the passed in String onto the GUI.
     * @param String to be shown in Alert
     */
    public void displayAlert(String message) {
        System.out.println(message);
        String[] errorData = myErrorResources.getString(message).split(",");
        Alert myAlert = new Alert(AlertType.INFORMATION);
        myAlert.setTitle(errorData[0]);
        myAlert.setHeaderText(null);
        myAlert.setContentText(errorData[1]);
        myAlert.showAndWait();
    }

}
