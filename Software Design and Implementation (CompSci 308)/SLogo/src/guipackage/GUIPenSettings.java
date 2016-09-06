package guipackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * Creates toggles to graphically select pen settings. 
 */
public class GUIPenSettings implements IGUIObject{
	private static final int PEN_TYPE_BUTTONS = 3;
	private static final int PEN_UPDOWN_BUTTONS = 2;
	private static final int PADDING = 10;
	private static final int BOX_SPACING = 10;
	private ResourceBundle myResources;
	private GUICanvas myCanvas;
	private GUICanvasPen myPen;
	private ToggleGroup penUpDownGroup;
	private ToggleGroup penTypeGroup;
	private Label penLabel;
	private TextField penThickness;
	private Button setThickness;
	private VBox vbox1;
	private VBox vbox2;
	
	public GUIPenSettings(ResourceBundle r, GUICanvas c) {
		myResources = r;
		myCanvas = c;
		myPen = myCanvas.getPen();
	}
	/**
	 * Returns VBox with ToggleGroup for each type of pen setting. 
	 */
	@Override
	public Node createNode() {
		VBox vboxToReturn = new VBox(BOX_SPACING);
		HBox hbox = new HBox(BOX_SPACING);
		setPenUpDownVBox();
		setPenTypeVBox();
		setThicknessNodes();
		hbox.getChildren().addAll(vbox1,vbox2);
		vboxToReturn.getChildren().addAll(hbox, penLabel, penThickness, setThickness);		
		vboxToReturn.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
		return vboxToReturn;
	}
	/**
	 * Create ToggleGroup for pen up/down status.
	 */
	private void setPenUpDownVBox() {
		penUpDownGroup = new ToggleGroup();
		penUpDownGroup.selectedToggleProperty().addListener(
				e -> myCanvas.setTurtlePenStatus(penUpDownGroup.getSelectedToggle().getUserData().toString()));
		GUIToggleGroup newGroup = new GUIToggleGroup(myResources.getString("UpDownLabel"), penUpDownGroup,
				PEN_UPDOWN_BUTTONS, new ArrayList<>(Arrays.asList(myResources.getString("PenUp"),
											myResources.getString("PenDown"))), 0);
		vbox1 = newGroup.createToggleGroupVBox();		
	}
	/**
	 * Create ToggleGroup for pen type.
	 */
	private void setPenTypeVBox() {
		penTypeGroup = new ToggleGroup();
		penTypeGroup.selectedToggleProperty().addListener(
				e -> myPen.setMyPenType(penTypeGroup.getSelectedToggle().getUserData().toString()));
		GUIToggleGroup newGroup = new GUIToggleGroup(myResources.getString("PenTypeLabel"), penTypeGroup,
				PEN_TYPE_BUTTONS, new ArrayList<>(Arrays.asList(myResources.getString("SolidPen"),
											myResources.getString("DashedPen"),
											myResources.getString("DottedPen"))), 0);
		vbox2 = newGroup.createToggleGroupVBox();
	}
	/**
	 * Create textfield so user can set pen thickness. 
	 */
	private void setThicknessNodes() {
		penLabel = new Label(myResources.getString("ThicknessFieldLabel"));
		penThickness = new TextField();
		setThickness = new Button(myResources.getString("SetThickness"));
		setThickness.setOnAction(event -> myPen.setMyPenSize(Double.valueOf(penThickness.getText())));
	}

	@Override
	public void updateNode() {
	}

}
