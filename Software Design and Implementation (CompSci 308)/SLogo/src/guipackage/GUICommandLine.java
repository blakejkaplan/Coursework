package guipackage;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ResourceBundle;

import controller.Controller;

/**
 * Class for Command Line Node on GUI.
 */
public class GUICommandLine {
	private Controller myController;
	private ResourceBundle myResources;
	private TabMainScreen myMainScreen;
	
	private Label commandLabel;
	private TextArea commandInputLine;
	private Button runButton;
	private VBox commandLine;
	
	private static final int TEXT_AREA_ROWS = 3;
	private static final int COMMAND_LINE_SPACING = 5;
	private static final double PADDING_TOP = 0;
	private static final double PADDING_RIGHT = 10;
	private static final double PADDING_BOTTOM = 10;
	private static final double PADDING_LEFT = 10;
	
	protected GUICommandLine(Controller c, ResourceBundle r, TabMainScreen t) {
		myController = c;
		myResources = r;
		myMainScreen = t;
	}
	
	/**
	 * Creates all necessary elements of the Node for the Command Line.
	 * @return Command Line Node
	 */
	protected Node createNode() {
		commandLabel = new Label(myResources.getString("Command"));
		commandInputLine = new TextArea();
		commandInputLine.setPrefRowCount(TEXT_AREA_ROWS);
		runButton = new Button(myResources.getString("Run"));
		runButton.setOnAction(evt -> runCommand(commandInputLine.getText()));
		
		commandLine = new VBox();
		commandLine.getChildren().addAll(commandLabel, commandInputLine, runButton);
		commandLine.setSpacing(COMMAND_LINE_SPACING);
		commandLine.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
		
		return commandLine;
	}
	
	/**
	 * Runs the command that user inputs into the TextArea.
	 * @param User command
	 */
	protected void runCommand(String command) {
		myController.processCommand(command);
		myMainScreen.updateGUI();
		clearTextField();
	}
	
	/**
	 * Clears the command line
	 */
	private void clearTextField() {
		commandInputLine.clear();
	}
}
