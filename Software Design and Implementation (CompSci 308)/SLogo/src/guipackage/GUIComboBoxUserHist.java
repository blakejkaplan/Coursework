package guipackage;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import controller.Controller;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import model.Command;
import model.CommandDictionary;

/**
 * Create ComboBox to hold history of user defined commands.
 */

public class GUIComboBoxUserHist extends GUIComboBox {
    private CommandDictionary myUserDefinedCommands;
    private TextInputDialog dialog;

    public GUIComboBoxUserHist(GUICanvas canvas, ResourceBundle myResources, Controller myController,
                                     String promptText, GUICommandLine myCommandLine, CommandDictionary myComDict) {
        super(canvas, myResources, myController, promptText, myCommandLine);
        myUserDefinedCommands = myComDict;
    }
    
    /**
     * Returns list of user defined commands.
     */
    @Override
    protected List<String> optionsList() {
        List<String> userDefinedCommands = new ArrayList<>();
        for (String s : myUserDefinedCommands.getCommandKeySet()) {
            userDefinedCommands.add(s);
        }
        return userDefinedCommands;
    }
    /**
     * Sets action so that on comboButton click, selected user defined command will be executed.
     */
    @Override
    protected void setButtonAction() {
        comboButton.setOnAction(event -> {
            createDialog();
            dialog.showAndWait();
            myController.processCommand(getCommandToRun());
        });
    }
    /**
     * For user defined commands that require input of arguments, creates popup dialog to get arguments from user.
     */
    private void createDialog() {
        Command command = myUserDefinedCommands.getCommandFor(comboBox.getValue());
        int numArgs = command.getParams().size();
        dialog = new TextInputDialog();
        dialog.setTitle(numArgs + " " + myResources.getString("ArgumentMessage") + " " + command);
        dialog.setHeaderText(myResources.getString("ArgumentsNeeded") + command.getParams());
        dialog.setContentText(myResources.getString("RequestArguments"));
    }
    /**
     * Gets executable user defined command filled in with appropriate arguments from the user. 
     * @return
     */
    private String getCommandToRun() {
        String commandToRun = comboBox.getValue();
        String[] parameters = dialog.getResult().split(" ");
        for (String parameter : parameters) {
            commandToRun = commandToRun + " " + parameter;
        }
        return commandToRun;
    }
    /**
	 * Returns empty (not null) Node to be used as icon in the CommandHistory ComboBox.
	 */
    @Override
	protected Node getNodeForBox(String item) {
		return new Label(NO_NODE_FOR_BOX);
	}
}
