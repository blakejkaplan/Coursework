package guipackage;

import java.util.List;
import java.util.ResourceBundle;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 * Abstract class to implement different types of ComboBoxes.
 */

public abstract class GUIComboBox implements IGUIObject {
	private static final int COMBOBOX_WIDTH = 190;
	private static final int VISIBLE_ROW_COUNT = 5;
	private static final int PADDING = 10;
	private static final int HBOX_SPACING = 5;
	protected static final String NO_NODE_FOR_BOX = "";
	protected String promptText;
	protected ResourceBundle myResources;
	protected Controller myController;
	protected ObservableList<String> options;
	protected ComboBox<String> comboBox;
	protected GUICommandLine myCommandLine;
	protected Button comboButton;
	protected GUICanvas canvas;
	protected String paletteSource;
	
	public GUIComboBox(GUICanvas canvas, ResourceBundle myResources, Controller myController, String promptText, GUICommandLine myCommandLine) {
		this.canvas = canvas;
		this.myResources = myResources;
		this.myController = myController;
		this.promptText = promptText;
		this.myCommandLine = myCommandLine;
	}
	
	public GUIComboBox(GUICanvas canvas, ResourceBundle myResources, String promptText){
		this.canvas = canvas;
		this.myResources = myResources;
		this.promptText = promptText;
	}
	
	public GUIComboBox(GUICanvas canvas, ResourceBundle myResources, String promptText, String paletteSource){
		this.canvas = canvas;
		this.myResources = myResources;
		this.promptText = promptText;
		this.paletteSource = paletteSource;
	}
	
	/**
	 * Creates ComboBox Node.
	 */
	@Override
	public Node createNode(){
		HBox hbox = new HBox(HBOX_SPACING);
		options = FXCollections.observableArrayList(
			        optionsList()
			    );
		comboBox = new ComboBox<>(options);
		comboBox.setVisibleRowCount(VISIBLE_ROW_COUNT);
		comboBox.setPrefWidth(COMBOBOX_WIDTH);
		comboBox.setPromptText(promptText);
		comboBox.setCellFactory(factory -> new MyCustomCell());
		comboButton = new Button("Go");
		setButtonAction();
		hbox.getChildren().addAll(comboBox, comboButton);
		hbox.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
		return hbox;
	}
	
	/**
	 * Sets action when button is pressed.
	 */
	protected abstract void setButtonAction();
	
	/**
	 * Creates custom cell factory for ComboBox
	 */
	private class MyCustomCell extends ListCell<String> {
        @Override 
    	protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
        } else {
       	 	HBox hbox = new HBox();
       	 	Label lbl = new Label(item);
       	 	hbox.getChildren().addAll(getNodeForBox(item), lbl);
            setGraphic(hbox);
        }
       }
    }
	
	/**
	 * Sets icon for ComboBox
	 * @param item
	 * @return
	 */
	protected abstract Node getNodeForBox(String item);
	
	/**
	 * Updates Node whenever new information or data is available.
	 */
	@Override
	public void updateNode() {
		ObservableList<String> newOptions = FXCollections.observableArrayList(
		        optionsList()
		    );
		comboBox.setItems(newOptions);
	}
	
	/**
	 * List that contains Data for each ComboBox.
	 * @return
	 */
	protected abstract List<String> optionsList();
	
	public void changePalette(String RGB, int index) {
		List<String> currentPalette = comboBox.getItems();
		currentPalette.set(index, RGB);
		updateNode();
	}
	/**
	 * Returns list of items in the ComboBox.
	 * @return
	 */
	public List<String> getOptionsList(){
		return optionsList();
	}

}
