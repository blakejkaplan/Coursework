package guipackage;

import java.util.ResourceBundle;
import javafx.scene.paint.Color;

/**
 * Editable color palette for pen color.
 */
public class GUIComboBoxColorP extends GUIComboBoxColor {

	public GUIComboBoxColorP(GUICanvas canvas, ResourceBundle myResources,
			String promptText, String paletteSource) {
		super(canvas, myResources, promptText, paletteSource);
	}
	/**
	 * On comboButton click, canvas pen will be set to new color. 
	 */
	@Override
	protected void setCanvasValues(Color col) {
		canvas.getPen().setMyPenColor(col, comboBox.getValue());
	}
	
}
