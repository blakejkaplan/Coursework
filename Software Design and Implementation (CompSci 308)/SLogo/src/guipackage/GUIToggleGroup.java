package guipackage;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
/**
 * Returns VBox containing toggle group and a constructor specified number of RadioButtons
 */
public class GUIToggleGroup {
	private static final int PADDING = 10;
	private static final int BOX_SPACING = 10;
	private static final String FONT_STYLE = "-fx-font: 10px \"Sans Serif\"";
	private Label myToggleLabel;
	private ToggleGroup myGroup;
	private List<RadioButton> myButtons;
	private List<String> myButtonData;
	private int myTrueButtonIndex;
	private VBox toReturn;
	private int numButtons;
	
	public GUIToggleGroup(String toggleLabelString, ToggleGroup group, int numButtons,
			List<String> buttonUserData, int trueButtonIndex) {
		this.myToggleLabel = new Label(toggleLabelString);
		this.myGroup = group;
		this.numButtons = numButtons;
		this.myButtons = new ArrayList<>();
		this.myButtonData = buttonUserData;
		this.myTrueButtonIndex = trueButtonIndex;
	}
	/**
	 * Returns VBox containing ToggleGroup of RadioButtons.
	 * @return
	 */
	public VBox createToggleGroupVBox(){
		toReturn = new VBox(BOX_SPACING);
		toReturn.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
		initButtons();
		toReturn.getChildren().add(myToggleLabel);
		for(RadioButton bt: myButtons){
			toReturn.getChildren().add(bt);
		}
		setFontStyle();
		return toReturn;
	}
	/**
	 * Based on the constructor specified number of RadioButtons desired, creates this many RadioButtons with data as specified
	 * by given list of button data. 
	 */
	private void initButtons(){
		for(int i=0;i<numButtons;i++){
			RadioButton newBT = new RadioButton(myButtonData.get(i));
			newBT.setUserData(myButtonData.get(i));
			newBT.setToggleGroup(myGroup);
			if(i==myTrueButtonIndex) {
				newBT.setSelected(true);
			}
			myButtons.add(newBT);
		}
	}
	/**
	 * Sets font style of all text in this VBox.
	 */
	private void setFontStyle(){
		for(Node child: toReturn.getChildren()){
			child.setStyle(FONT_STYLE);
		}
	}

}
