package guipackage;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Creates a ColorPicker that allows user to choose color for something.
 */

abstract class GUIColorPicker implements IGUIObject {
	private static final int VBOX_PADDING = 5;
	private String pickerLabel;
	protected GUICanvas canvas;
	
	public GUIColorPicker(GUICanvas canvas, String pickerLabel) {
		this.pickerLabel = pickerLabel;
		this.canvas = canvas; 
	}
	
	/**
	 * Creates Color Picker and returns it as a Node.
	 */
	@Override
	public Node createNode() {
		VBox box = new VBox();
        box.setPadding(new Insets(VBOX_PADDING, VBOX_PADDING, VBOX_PADDING, VBOX_PADDING));          
    	ColorPicker colorPicker = new ColorPicker(getStartColor());
		colorPicker.setOnAction(event -> handleEvent(colorPicker));
        
        Label text = new Label(pickerLabel);
        box.getChildren().addAll(text, colorPicker);
        return box;
	}
	
	/**
	 * Starting color for the ColorPicker
	 * @return Starting color
	 */
	protected abstract Color getStartColor();
	
	/**
	 * Tells GUI what to do after a color is chosen.
	 * @param colorPicker
	 */
	protected abstract void handleEvent(ColorPicker colorPicker);
	
	/**
	 * Update node method. Never really need to update.
	 */
	@Override
	public void updateNode() {
	}
}
