package guipackage;

import java.util.List;
import java.util.ResourceBundle;

import controller.Controller;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Create a ComboBox to hold command history. 
 */

public class GUIComboBoxCommandHist extends GUIComboBox {
	public GUIComboBoxCommandHist(GUICanvas canvas, ResourceBundle myResources, Controller myController,
			String promptText, GUICommandLine myCommandLine) {
		super(canvas, myResources, myController, promptText, myCommandLine);
	}
	
	/**
	 * Returns list of previously run commands. 
	 */
	@Override
	protected List<String> optionsList() {
		return myController.getCommandHistory();
	}
	/**
	 * Sets action so that on comboButton click, the selected previous command will be exectued. 
	 */
	@Override
	protected void setButtonAction(){
		comboButton.setOnAction(event -> {
			myCommandLine.runCommand(comboBox.getValue());
		});
	}
	/**
	 * Returns empty (not null) Node to be used as icon in the CommandHistory ComboBox.
	 */
	@Override
	protected Node getNodeForBox(String item) {
		return new Label(NO_NODE_FOR_BOX);
	}
	
}
