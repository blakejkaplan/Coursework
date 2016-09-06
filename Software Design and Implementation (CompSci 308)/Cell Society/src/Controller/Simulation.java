package Controller;

import java.io.File;
import java.util.ResourceBundle;
import Model.Cell;
import Model.Grid;
import Model.InfiniteGrid;
import Model.StandardGrid;
import Model.ToroidalGrid;
import Rules.Rules;
import View.CSView;
import XML.XMLGenerator;
import XML.XMLParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

public class Simulation {
	private boolean running;
	private boolean loaded;
	private int msDelay;
	private int time;
	private int mySpeed;
	private XMLParser parser;

	// xml determined variables
	private File xmlFile;
	private Grid myGrid;
	private Rules myRules;
	private int rows;
	private int cols;
	private String[][] inputgrid;
	private String gridType;

	private CSView myView;

	public static final String DEFAULT_CONTROLLER_RESOURCE = "Controller/Controller";
	public static final String ERROR_RESOURCE = "Controller/ErrorMessages";
	private ResourceBundle myControllerResources;
	private ResourceBundle myErrorResources;

	public Simulation() {
		myControllerResources = ResourceBundle.getBundle(DEFAULT_CONTROLLER_RESOURCE);
		mySpeed = Integer.parseInt(myControllerResources.getString("InitialSpeed"));
		msDelay = Integer.parseInt(myControllerResources.getString("MsDelay"));
		myErrorResources = ResourceBundle.getBundle(ERROR_RESOURCE);
		time = 0;

		// sets the simulation's loop
		KeyFrame frame = new KeyFrame(Duration.millis(msDelay), e -> step(false));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	/**
	 * Returns name of the program.
	 */
	public String getTitle() {
		return myControllerResources.getString("Title");
	}

	/**
	 * 
	 * @param myFile input file to be parsed
	 * @return boolean of returns whether or not xml file has been parsed and data loaded
	 * 
	 */
	public boolean useParser(File file) {
		parser = new XMLParser(this);
		if (!parser.parse(file))
			return false;
		xmlFile = file;
		loadFromXML();
		return true;
	}

	/**
	 * Method that loads information from current parser
	 */
	public void loadFromXML() {
		inputgrid = parser.getGrid();
		rows = inputgrid[1].length;
		cols = rows;
		myRules = parser.getRules(); 
		getGridObject();
		myRules.populateStatesInfo();
		myRules.initGrid(myGrid, inputgrid);
		loaded = true;
	}
	
	/**
	 * Sets the grid instance variable to be the correct instance of the Grid
	 * abstract class
	 */
	public void getGridObject() {
		gridType = parser.getGridType();
		switch (gridType) {
		case "Standard":
			myGrid = new StandardGrid(rows, cols, inputgrid);
			break;
		case "Toroidal":
			myGrid = new ToroidalGrid(rows, cols, inputgrid);
			break;
		case "Infinite":
			myGrid = new InfiniteGrid(rows, cols, inputgrid, myRules);
			break;
		default:
			System.out.println("THAT IS NOT AN OPTION!");
		}
	}

	/**
	 * Saves a XML file
	 */
	public void saveXML() {
		running = false;
		if (!loaded) {
			Alert myAlert = new Alert(AlertType.INFORMATION);
			myAlert.setTitle("Saving Error");
			myAlert.setHeaderText(null);
			myAlert.setContentText("You must have a simulation loaded to save!");
			myAlert.showAndWait();
			return;
		}
		XMLGenerator myGenerator = new XMLGenerator();
		String myRulesName = myRules.toString().replaceAll(" ", "");
		File myFile = myView.promptForFileName();
		if (myFile == null)
			return;
		myGenerator.save(myRulesName, myGrid.getNumRows(), myGrid.getNumCols(), myGrid.getGrid(), myRules.getParameters(), myFile, gridType);
	}

	/**
	 * Applies rules to cells, updates their states, displays new states Fastest
	 * is every 100 ms, slowest is every 2 seconds. Changing the speed will not
	 * change the time on the graph- i.e. 2 seconds on graph will always be 2
	 * seconds on graph, However, changes in cell # will be spread of great
	 * delta time
	 */
	public void step(boolean stepping) {
		if (running || stepping) {
			if (time % ((21 - mySpeed) * 100) != 0 && !stepping) {
				time += msDelay;
				return;
			}
			time += msDelay;
			applyRulesToGrid();
			updateEachState();
			myView.updateUI();
		}
	}

	/**
	 * Helper method that applies the specified rules to each cell in the grid (accommodates for resizing in the case
	 * of an infinite grid).
	 */
	public void applyRulesToGrid(){
		int rows = myGrid.getNumRows();
		int cols = myGrid.getNumCols();
		int r0 = 0;
		int c0 = 0;
				
		for(int r = 0; r < rows; r++){
			if (r == 0) {
				r = r0;
			}
			for(int c = 0; c < cols; c++){
				if (c == 0) {
					c = c0;
				}
				myRules.applyRulesToCell(myGrid.getCell(r,c), myGrid);
				if (myGrid.hasBeenResizedImmediatelyBefore()) {
					r++;
					c++;
					r0++;
					c0++;
					rows++;
					cols++;
					myGrid.setResizedImmediatelyBefore(false);
				}
			}
		}
		myGrid.setResizedThisStep(false);
	}

	/**
	 * Helper method that updates each state that needs to be updated
	 * Then clears the update list.
	 */
	private void updateEachState() {
		for (Cell c : myRules.getToBeUpdatedList()) {
			myRules.updateStateCount(c);
			c.updateState();
		}
		myRules.clearToBeUpdatedList();
	}

	/**
	 * returns the current speed
	 */
	public int getSpeed() {
		return mySpeed;
	}

	/**
	 * setter method to change current speed by del
	 * 
	 * @param del
	 * @return returns new speed
	 */
	public int changeSpeed(int del) {
		mySpeed += del;
		return mySpeed;
	}

	/**
	 * @return returns the current simulation
	 */
	public String getName() {
		return myRules.toString();
	}

	/**
	 * sets the current state of running to the value of b
	 * 
	 * @param b
	 *            a boolean indicating running or not
	 */
	public void setRunning(boolean b) {
		running = b;
	}

	/**
	 * @return returns the grid that this simulation is using
	 */
	public Grid getGrid() {
		return myGrid;
	}

	/**
	 * @return returns the rules that this simulation is using Applies rules to
	 *         cells, updates their states, displays new states
	 */
	public Rules getRules() {
		return myRules;
	}

	/**
	 * @return returns the XMLFile that this simulation is using
	 */
	public File getXML() {
		return xmlFile;
	}

	/**
	 * @param sets
	 *            the myView instance variable equal to v
	 */
	public void setView(CSView v) {
		myView = v;
	}

	/**
	 * 
	 * @return returns the number of milliseconds since current execution start
	 */
	public int getTime() {
		return time;
	}

	public void displayAlert(String message) {
		String[] errorData = myErrorResources.getString(message).split(",");
		Alert myAlert = new Alert(AlertType.INFORMATION);
		myAlert.setTitle(errorData[0]);
		myAlert.setHeaderText(null);
		myAlert.setContentText(errorData[1]);
		myAlert.showAndWait();
	}

}