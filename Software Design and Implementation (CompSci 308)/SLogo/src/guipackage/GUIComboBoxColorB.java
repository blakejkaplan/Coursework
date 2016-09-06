package guipackage;

import java.util.ResourceBundle;

import javafx.scene.paint.Color;
/**
 * Editable color palette for background color.
 */
public class GUIComboBoxColorB extends GUIComboBoxColor {	
	
	public GUIComboBoxColorB(GUICanvas canvas, ResourceBundle myResources,
			String promptText, String paletteSource) {
		super(canvas, myResources, promptText, paletteSource);
	}
	
	/**
	 * On comboButton click, canvas background will be set to new color. 
	 */
	@Override
	protected void setCanvasValues(Color col) {
		canvas.getBackgroundCanvas().setBackgroundColor(col, comboBox.getValue());
	}
	
}
