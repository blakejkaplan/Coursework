package guipackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import controller.Controller;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Create ComboBox to hold interpretable languages. 
 */

public class GUIComboBoxLanguages extends GUIComboBox {

	public GUIComboBoxLanguages(GUICanvas canvas, ResourceBundle myResources, Controller myController,
			String promptText, GUICommandLine myCommandLine) {
		super(canvas, myResources, myController, promptText, myCommandLine);
	}
	/**
	 * Returns list of interpretable languages.
	 */
	@Override
	protected List<String> optionsList() {
		return new ArrayList<>(Arrays.asList(myResources.getString("Languages").split(" ")));
	}
	/**
	 * Set action so that on comboButton click, selected language will be come new interpreted language for the console.
	 */
	@Override
	protected void setButtonAction(){
		comboButton.setOnAction(event -> myController.setLanguage(comboBox.getValue()));
	}
	/**
	 * Returns empty (not null) Node to be used as icon in the CommandHistory ComboBox.
	 */
	@Override
	protected Node getNodeForBox(String item) {
		return new Label(NO_NODE_FOR_BOX);
	}

}
