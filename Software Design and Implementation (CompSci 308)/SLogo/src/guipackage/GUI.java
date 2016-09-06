package guipackage;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Turtle;

/**
 * Main GUI class. Creates main JavaFX components for GUI.
 */
public class GUI implements IGUI {
    private static final String GUI_RESOURCE = "GUI";
	private static final String HELP_TAB_TEXT1 = "Basic Help";
	private static final String HELP_TAB_TEXT2 = "Extended Help";
	private static final double TABS_OFFSET = 5.0;
	private static final double NEWTAB_OFFSET = 40.0;
    private static final String WORKSPACE = "Workspace";
    private static final String BASIC_COMMANDS = "BasicCommands";
    private static final String EXTENDED_COMMANDS = "ExtendedCommands";
    private static final String CREATE_NEW_TAB = "Create New Tab";
    private Scene myScene;
	private AnchorPane myRoot;
	private TabPane myTabs;
	private ResourceBundle myResources;
	private Turtle myTurtle;
    private Stage myStage;
	
	private int windowHeight;
	private int windowWidth;

	public GUI(int windowWidth, int windowHeight, Stage stage) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.myResources = ResourceBundle.getBundle(GUI_RESOURCE);
        myStage = stage;
	}
	
	/**
	 * Creates the scene to be put on the stage.
	 * @return GUI Scene
	 */
	public Scene createScene() {
		myRoot = new AnchorPane();
		myTabs = new TabPane();
		Button newTab = new Button(CREATE_NEW_TAB);
		newTab.setOnAction(event -> createNewTab());
		
		TabMainScreen mainScreen = new TabMainScreen(myResources.getString(WORKSPACE) + (myTabs.getTabs().size()));
		TabHelp help1 = new TabHelp(myResources.getString(BASIC_COMMANDS), HELP_TAB_TEXT1);
		TabHelp help2 = new TabHelp(myResources.getString(EXTENDED_COMMANDS), HELP_TAB_TEXT2);
		
		myTabs.getTabs().addAll(mainScreen.getTab(myStage), help1.getTab(), help2.getTab());
		
		AnchorPane.setTopAnchor(myTabs, TABS_OFFSET);
		AnchorPane.setTopAnchor(newTab, NEWTAB_OFFSET);
		
		myRoot.getChildren().addAll(myTabs, newTab);
	
		myScene = new Scene(myRoot, windowHeight, windowWidth, Color.WHITE);
		return myScene;
	}
	
	private void createNewTab() {
		Tab tab = new TabMainScreen(myResources.getString(WORKSPACE) + (myTabs.getTabs().size()-1)).getTab(myStage);
        myTabs.getTabs().add(tab);
        myTabs.getSelectionModel().select(tab);
	}
	
	/**
	 * Returns width of window
	 */
	@Override
	public int getWidth() {
		return windowWidth;
	}

	/**
	 * Returns height of window
	 */
	@Override
	public int getHeight() {
		return windowHeight;
	}
	
	/**
	 * Notifies the turtle's observers that the turtle has changed.
	 */
	@Override
	public void notifyAllObservers() {
		myTurtle.notifyObservers();
	}

}