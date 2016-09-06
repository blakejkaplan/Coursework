package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import guipackage.GUIAlert;
import guipackage.GUICanvas;
import guipackage.GUILabeled;
import javafx.stage.Stage;
import model.CommandDictionary;
import model.IFunctions;
import model.Turtle;
import model.VariableDictionary;
import org.xml.sax.SAXException;
import xml.XMLParser;
import xml.XMLSaver;

import javax.xml.parsers.ParserConfigurationException;

/**
 * This class is the only external-facing back end class. It facilitates the interaction between the front end and back end
 * by processing the commands inputed by the user in the front end.
 */
public class Controller {

    private static final String SYNTAX_RESOURCE = "resources/languages/Syntax";
    private static final String DEFAULT_LANGUAGE_RESOURCE = "resources/languages/English";
    private static final String LANGUAGE_RESOURCE_LOCATION = "resources/languages/";
    public static final String DOES_NOT_EXIST = "DoesNotExist";
    public static final String EXECUTION_ERROR = "ExecutionError";
    public static final String INVALID_SYNTAX = "InvalidSyntax";
    private static final String GUI_RESOURCE = "GUI";
    public static final String PARSING_ERROR = "ParsingError";
    private static final String WHITESPACE = "\\p{Space}";
    private static final String NO_FILE = "NoFile";
    private String myLanguageResource;
    private Parser myParser;
    private XMLParser myXMLParser;
    private List<Turtle> myTurtles;
    private List<String> myCommandHistory;
    private GUILabeled myOutput;
    private GUIAlert myAlert;
    private GUICanvas myCanvas;
    private ResourceBundle myGUIResource;
    private CommandDictionary commandDict;
    private VariableDictionary varDict;
    private Stage myStage;

    /**
     * Creates a controller with a reference to the workspace's canvas and the workspace's stage.
     * @param canvas: workspace's canvas.
     * @param stage: workspace's stage.
     */
    public Controller(GUICanvas canvas, Stage stage) {
        myCanvas = canvas;
        myStage = stage;
        init();
    }

    /**
     * Initializes the controller.
     */
    public void init() {
        myLanguageResource = DEFAULT_LANGUAGE_RESOURCE;
        myCommandHistory = new ArrayList<>();
        myTurtles = new ArrayList<>();
        addInitialTurtle();
        myGUIResource = ResourceBundle.getBundle(GUI_RESOURCE);
        myOutput = new GUILabeled(myGUIResource, "Output");
        myAlert = new GUIAlert();
        commandDict = new CommandDictionary();
        varDict = new VariableDictionary();
        myParser = new Parser(commandDict, varDict, myCanvas);
        myParser.addPatterns(myLanguageResource);
        myParser.setCurrentLanguage(myLanguageResource);
        myParser.addPatterns(SYNTAX_RESOURCE);
    }

    /**
     * Add initial turtle with ID 1.
     */
    public void addInitialTurtle() {
        Turtle turtle = new Turtle(1);
        turtle.setActive(true);
        turtle.addObserver(myCanvas);
        turtle.updateObservers();
        myTurtles.add(turtle);
    }

    /**
     * Sets the parser language.
     *
     * @param lang: user-selected language.
     */
    public void setLanguage(String lang) {
        myLanguageResource = LANGUAGE_RESOURCE_LOCATION + lang;
        myParser.clearAllPatterns();
        myParser.addPatterns(myLanguageResource);
        myParser.setCurrentLanguage(myLanguageResource);
        myParser.addPatterns(SYNTAX_RESOURCE);
    }

    /**
     * Processes the command.
     *
     * @param command: String inputed by user to the command line.
     * @throws ClassNotFoundException
     */
    public void processCommand(String command){
    	List<String> commandAsList = getCommandAsList(command);
    	while (!commandAsList.isEmpty()) {
    		try{
    			IFunctions commandToExecute = myParser.createCommandTree(commandAsList, myTurtles);
    			if (commandToExecute != null) {
    				double result = executeCommandTree(commandToExecute);
    				myOutput.setOutputText(Double.toString(result));
    				addCommandToHistory(command);
    			}
    		}
    		catch(ClassNotFoundException e){
    			myAlert.displayAlert(DOES_NOT_EXIST);
    		}
    		catch(IndexOutOfBoundsException e){
    			myAlert.displayAlert(INVALID_SYNTAX);
    		}
    		catch(NullPointerException e){
    			myAlert.displayAlert(EXECUTION_ERROR);
    		}
    	}
    }


    /**
     * Loads an XML file containing user's workspace preferences.
     * @param myFile: XML file to load.
     */
    public void loadXML(File myFile) {
        myXMLParser = new XMLParser();
        try {
            myXMLParser.parse(myFile);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            myAlert.displayAlert(PARSING_ERROR);
        } catch (IllegalArgumentException e){
            myAlert.displayAlert(NO_FILE);
        }
    }

    /**
     * Parses string input into a list by splitting based on white space. Ignores lines that're comments.
     * @param command: string of commands to convert to a list.
     * @return
     */
    private List<String> getCommandAsList(String command) {
    	List<String> inputCommandList = new ArrayList<>();
    	String[] inputArray = command.split("\n");
    	for (int i = 0; i < inputArray.length; i++) {
    		if (!inputArray[i].trim().startsWith("#")) {
    			String[] toAdd = inputArray[i].split(WHITESPACE);
    			for (int j = 0; j < toAdd.length; j++) { 
    				if (!toAdd[j].isEmpty()) {
    					inputCommandList.add(toAdd[j].trim());
    				}
    			}
    		}

    	}
    	return inputCommandList;
    }

    /**
     * Interprets a command tree.
     * @param head: head node of the command tree.
     * @return the final output value of the executed command.
     * @throws ClassNotFoundException
     */
    private double executeCommandTree(IFunctions head) throws ClassNotFoundException {
        double result = head.interpret(commandDict, varDict);
        addObserver();
        return result;
    }

    /**
     * Adds the canvas as the observer to all new turtles.
     */
    public void addObserver() {
        for (Turtle t : myTurtles) {
            if (t.countObservers() == 0) {
                t.addObserver(myCanvas);
                t.updateObservers();
            }
        }
    }

    /**
     * Gets the list of existing turtles.
     * @return list of existing turtles.
     */
    public List<Turtle> getTurtles() {
        return myTurtles;
    }

    /**
     * Adds a command to the user's command history.
     * @param command: command to add.
     */
    private void addCommandToHistory(String command) {
        myCommandHistory.add(command);
    }

    /**
     * Gets the user's command history.
     * @return list of user's recently executed commands.
     */
    public List<String> getCommandHistory() {
        return myCommandHistory;
    }

    /**
     * Gets the output element for the GUI.
     * @return output element for GUI.
     */
    public GUILabeled getGUIOutput() {
        return myOutput;
    }

    /**
     * Displays an alert indicating an error.
     * @param errorResourceKey: key for error message in the Errors.properties file.
     */
    public void displayAlert(String errorResourceKey) {
        myAlert.displayAlert(errorResourceKey);
    }

    /**
     * Gets the current workspace's command dictionary.
     * @return current workspace's command dictionary.
     */
    public CommandDictionary getCommandDictionary() {
        return commandDict;
    }

    /**
     * Gets the current workspace's variable dictionary.
     * @return current workspace's variable dictionary.
     */
    public VariableDictionary getVariableDictionary() {
        return varDict;
    }
    
    /**
     * Gets the XML Parser.
     * @return XML Parser.
     */
    public XMLParser getXMLParser() {
    	return myXMLParser;
    }

    /**
     * Saves the current workspace preferences.
     * @param file: file to write to.
     */
    public void save(File file) {
        XMLSaver mySaver = new XMLSaver(commandDict, varDict, this);
        mySaver.generateFile(myCanvas.getBackgroundCanvas().getBackgroundColor(), myCanvas.getPen().getMyPenRGB(), 
        		myCanvas.getTurtleImageView().getTurtleImageName(), file);
    }

    /**
     * Gets the current workspace's stage.
     * @return current workspace's stage.
     */
    public Stage getStage() {
        return myStage;
    }
}
