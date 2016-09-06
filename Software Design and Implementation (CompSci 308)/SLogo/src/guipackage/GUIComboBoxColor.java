package guipackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Abstract class to allow ComboBoxes that set some color value of the canvas.
 */
abstract class GUIComboBoxColor extends GUIComboBox{
	private static final int RECTANGLE_SIDE_SIZE = 20;
	protected List<String> palette;
	
	public GUIComboBoxColor(GUICanvas canvas, ResourceBundle myResources,
			String promptText, String paletteSource) {
		super(canvas, myResources, promptText, paletteSource);
		fillDefaultPalette();
	}
	/**
	 * Sets default color palette based on paletteSource, which is a list of colors from resource file.
	 */
	protected void fillDefaultPalette() {
		List<String> defaultColors = new ArrayList<>(Arrays.asList(paletteSource.split(",")));
		palette = defaultColors;
	}
	/**
	 * On comboButton click, specific features on canvas will have their color set to the ComboBox value.
	 */
	@Override
	protected void setButtonAction() {
		comboButton.setOnAction(event -> {
			String[] rgb = comboBox.getValue().split(" ");
			setCanvasValues(Color.rgb(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
			});
	}
	
	/**
	 * Specifies which canvas features will have color set. 
	 * @param col
	 */
	protected abstract void setCanvasValues(Color col);

	/**
	 * Create icon for each entry in the ComboBox that will show up next to its String value. 
	 */
	@Override
	protected Node getNodeForBox(String item){
		HBox hbox = new HBox();
		String[] rgb = item.split(" ");
	   	Color col = Color.rgb(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
	   	Rectangle rectangle = new Rectangle(RECTANGLE_SIDE_SIZE, RECTANGLE_SIDE_SIZE);
	   	rectangle.setFill(col);
	   	hbox.getChildren().addAll(new Label(options.indexOf(item) + " "), rectangle); 
	   	return hbox;
	}
	
	/**
	 *Returns current palette of colors. 
	 */
	@Override
	protected List<String> optionsList() {
		return palette;
	}
	/**
	 * Sets color at given index in current palette to given RGB value. 
	 */
	public void changePalette(String RGB, int index) {
		List<String> currentPalette = comboBox.getItems();
		if(index<currentPalette.size()){
			currentPalette.set(index, RGB);
		}
		palette = currentPalette;
		updateNode();
	}
}
