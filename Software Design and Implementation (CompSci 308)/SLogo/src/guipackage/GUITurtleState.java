package guipackage;

import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import model.Turtle;
/**
 * Shows current state of Turtle that is being hovered over. 
 */
public class GUITurtleState implements IGUIObject {
	private GUILabeled locationLabeled;
	private GUILabeled headingLabeled;
	private GUILabeled penLabeled;
	private GUILabeled activeLabeled;
	private Node locationNode;
	private Node headingNode;
	private Node penNode;
	private Node activeNode;
	private ResourceBundle myResources;
	private String fontStyle;
	
	
	public GUITurtleState(ResourceBundle myResources, GUILabeled locationLabel, GUILabeled headingLabel, 
			GUILabeled penLabel, GUILabeled activeLabel) {
		this.myResources = myResources; 
		this.locationLabeled = locationLabel;
		this.headingLabeled = headingLabel;
		this.penLabeled = penLabel;
		this.activeLabeled = activeLabel;
		this.fontStyle = myResources.getString("FontStyle");
	}
	/**
	 * Returns VBox that shows state of turtle: location, heading, pen status, active status. 
	 */
	@Override
	public Node createNode() {
		VBox vbox = new VBox();
		GUILabeled instructionLabel = new GUILabeled(myResources, myResources.getString("TurtleStateInstructions"));
		locationLabeled.setOutputText(myResources.getString("StartingTurtleLocation"));
		headingLabeled.setOutputText(myResources.getString("StartingTurtleHeading"));
		penLabeled.setOutputText(myResources.getString("StartingPenDownStatus"));
		activeLabeled.setOutputText(myResources.getString("StartingActiveStatus"));
		setFontStyle();
		vbox.getChildren().addAll(instructionLabel.createNode(), 
		locationNode, headingNode, penNode, activeNode);
		return vbox;
	}
	
	/**
	 * Sets font style of text in this state viewer. 
	 */
	private void setFontStyle(){
		locationNode = locationLabeled.createNode();
		headingNode = headingLabeled.createNode();
		penNode = penLabeled.createNode();
		activeNode = activeLabeled.createNode();
		locationNode.setStyle(fontStyle);
		headingNode.setStyle(fontStyle);
		penNode.setStyle(fontStyle);
		activeNode.setStyle(fontStyle);
	}

	@Override
	public void updateNode() {
	}
	
	/**
	 * Update turtle state viewer to show state of given Turtle. 
	 * @param turtle
	 */
	public void showTurtleState(Turtle turtle){
		locationLabeled.setOutputText(Math.round(turtle.getCurX()) + "," + Math.round(turtle.getCurY()));
		headingLabeled.setOutputText(""+Math.round(turtle.getDirection()));
		penLabeled.setOutputText(Boolean.toString(!turtle.isPenUp()));
		activeLabeled.setOutputText(Boolean.toString(turtle.isActive()));
	}

}
