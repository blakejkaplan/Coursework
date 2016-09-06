package guipackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
/**
 * Allows user to graphically select whether or not to show inactive Turtles on the canvas.
 */
public class GUITurtleStateToggle implements IGUIObject {
	private static final int TURTLE_STATE_BUTTONS = 2;
	private ToggleGroup toggleGroup;
	private ResourceBundle myResources;
	private GUICanvas canvas;
	
	public GUITurtleStateToggle(ResourceBundle myResources, GUICanvas canvas) {
		this.myResources = myResources;
		this.canvas = canvas;
	}
	/**
	 * Returns ToggleGroup and two RadioButtons to allow user to show or hide inactive Turtles. 
	 */
	@Override
	public Node createNode() {
		toggleGroup = new ToggleGroup();
		toggleGroup.selectedToggleProperty().addListener(
				e -> canvas.getTurtleImageView().setVisibility(toggleGroup.getSelectedToggle().getUserData().toString()));
		GUIToggleGroup toggleGroupObj = new GUIToggleGroup(myResources.getString("ShowHideLabel"), toggleGroup, TURTLE_STATE_BUTTONS,
				new ArrayList<>(Arrays.asList(myResources.getString("Show"),myResources.getString("Hide"))),
				0);
		return toggleGroupObj.createToggleGroupVBox();
	}

	@Override
	public void updateNode() {
	}

}
