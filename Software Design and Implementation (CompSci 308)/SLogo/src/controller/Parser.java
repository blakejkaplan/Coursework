package controller;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import guipackage.GUICanvas;
import model.*;

/**
 * This class is used to parse command strings into Command objects.
 */
public class Parser {

    private static final String CONSTANT = "Constant";
    private static final String VARIABLE = "Variable";
    private static final String COMMENT = "Comment";
    private static final String LIST_START = "ListStart";
    private static final String GROUP_START = "GroupStart";
    private static final String GROUP_END = "GroupEnd";
    private static final String LIST_END = "ListEnd";
    private static final String TURTLE_COMMANDS = "TurtleCommands";
    private static final String DISPLAY_COMMANDS = "DisplayCommands";
    private static final String COMMAND = "Command";
    private static final String MAKE_USER_INSTRUCTION = "MakeUserInstruction";
    private List<Entry<String, Pattern>> mySymbols;
    private static final String TURTLE_COMMANDS_RESOURCE = "controller/TurtleCommands";
    private static final String DISPLAY_COMMANDS_RESOURCE = "controller/DisplayCommands";
    private static final String NUM_CHILDREN_PER_COMMAND = "controller/NumChildrenForFunction";
    private static final String MODEL = "model.";
    private ResourceBundle myNumChildrenPerCommand;
    private ResourceBundle myTurtleCommands;
    private ResourceBundle myDisplayCommands;
    private List<Turtle> myCurTurtles;
    private VariableDictionary varDict;
    private CommandDictionary commandDict;
    private GUICanvas myCanvas;
    private String currentLanguage;

    /**
     * Creates a parser with the workspace's command dictionary, variable dictionary, and canvas.
     * @param myComDict: current workspace's command dictionary.
     * @param myVarDict: current workspace's variable dictionary.
     * @param canvas: current workspace's canvas.
     */
    public Parser(CommandDictionary myComDict, VariableDictionary myVarDict, GUICanvas canvas) {
        mySymbols = new ArrayList<>();
        myNumChildrenPerCommand = ResourceBundle.getBundle(NUM_CHILDREN_PER_COMMAND);
        myTurtleCommands = ResourceBundle.getBundle(TURTLE_COMMANDS_RESOURCE);
        myDisplayCommands = ResourceBundle.getBundle(DISPLAY_COMMANDS_RESOURCE);
        varDict = myVarDict;
        commandDict = myComDict;
        myCanvas = canvas;
    }
    
    /**
     * From regex example--adds regex patterns for the parser to recognize.
     * @param syntax: path to properties file.
     */
    public void addPatterns(String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(syntax);
        Enumeration<String> iter = resources.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String regex = resources.getString(key);
            mySymbols.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
    }
    
    /**
     * Clears all regex patterns stored in the parser.
     */
    public void clearAllPatterns() {
        mySymbols.clear();
    }

    /**
     * From regex example--gets the class name that matches the input text based on regex patterns.
     * @param text: text to interpret as a class name.
     * @return class name that matches the text.
     */
    public String getSymbol(String text) {
        final String ERROR = "NO MATCH";
        for (Entry<String, Pattern> e : mySymbols) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
        return ERROR;
    }

    /**
     * Trims input text and parses it to find the corresponding class name. 
     * @param text: text to interpret.
     * @return class name that matches the text based on regex.
     */
    private String parseText(String text) {
        if (text.trim().length() > 0) {
            return getSymbol(text);
        } else {
            //error
            return "";
        }
    }

    /**
     * Creates a tree of commands.
     * @param commandList: user-inputed command split into a list of strings.
     * @param curTurtles: list of existing turtles.
     * @return the head node of the command tree.
     * @throws ClassNotFoundException
     */
    public IFunctions createCommandTree(List<String> commandList, List<Turtle> curTurtles) throws ClassNotFoundException {
        myCurTurtles = curTurtles;
        return createCommandTreeFromList(commandList);
    }

    /**
     * Creates a tree of commands from a list of strings.
     * @param inputCommandList:  user-inputed command split into a list of strings.
     * @return the head node of the command tree.
     * @throws ClassNotFoundException
     */
    private IFunctions createCommandTreeFromList(List<String> inputCommandList) throws ClassNotFoundException {
        String commandToBuild = inputCommandList.get(0);
        return createClass(commandToBuild, inputCommandList);
    }

    /**
     * Creates a list of commands (i.e. [ commands ]).
     * @param inputList: remaining list of user input.
     * @return a CommandList whose children are command trees made of the commands contained within the square brackets.
     * @throws ClassNotFoundException
     */
    private CommandList createList(List<String> inputList) throws ClassNotFoundException {
        CommandList list = new CommandList();
        addChildrenToListOrGroupNode(list, inputList, LIST_END);
        return list;
    }
    
    /**
     * Creates a group of commands for unlimited parameters (i.e. ( command param1 param2 ... paramN)).
     * @param inputList: remaining list of user input.
     * @return a Node whose children are its parameters.
     * @throws ClassNotFoundException
     */
    private Node createGroup (List<String> inputList) throws ClassNotFoundException {
        Node group = getFunctionObject(parseText(inputList.get(0)));
        inputList.remove(0);
        addChildrenToListOrGroupNode(group, inputList, GROUP_END);
        return group;
    }
    
    /**
     * Adds child nodes to a command list or a group node.
     * @param node: node to add children to.
     * @param inputList: remaining list of user input.
     * @param endDelimiter: "]" for list, ")" for group.
     * @throws ClassNotFoundException
     */
    private void addChildrenToListOrGroupNode(Node node, List<String> inputList, String endDelimiter) throws ClassNotFoundException {
    	while (!(parseText(inputList.get(0))).equals(endDelimiter)) {
        	Node childHead;
        	if (parseText(inputList.get(0)).equals(LIST_START)) {
        		inputList.remove(0);
        		childHead = createList(inputList);
        	} else if (parseText(inputList.get(0)).equals(GROUP_START)) {
        		inputList.remove(0);
        		childHead = createGroup(inputList);
        	} else {
        		childHead = createClass(inputList.get(0), inputList);
        	}
            node.addChild(childHead);
        }
        inputList.remove(0);
    }

    /**
     * Creates the next command corresponding to the remaining strings in the list of user input.
     * @param commandToBuild: name of command to create.
     * @param inputCommandList: remaining list of user-inputed command.
     * @return the next command to be added to the command tree.
     * @throws ClassNotFoundException
     */
    private Node createClass(String commandToBuild, List<String> inputCommandList) throws ClassNotFoundException {
        String name = parseText(commandToBuild);
        Node node = null;
        inputCommandList.remove(0);
        switch (name) {
            case CONSTANT:
                node = new Constant(Integer.parseInt(commandToBuild));
                break;
            case VARIABLE:
                node = new Variable(commandToBuild);
                break;
            case COMMENT:
                break;
            case LIST_START:
            	node = createList(inputCommandList);
            	break;
            case GROUP_START:
            	node = createGroup(inputCommandList);
            	break;
            case COMMAND:
                node = handleCommand(commandToBuild, inputCommandList);
                break;
            case MAKE_USER_INSTRUCTION:
                node = new MakeUserInstruction(inputCommandList.get(0), currentLanguage);
                node.setNumChildrenNeeded(Integer.parseInt(myNumChildrenPerCommand.getString(name)));
                inputCommandList.remove(0);
                addChildrenToNode(node, inputCommandList);
                break;
            default:
                node = getFunctionObject(name);
                addChildrenToNode(node, inputCommandList);
                break;
        }
        return node;
    }

    /**
     * Handles the parsing of a user-defined command.
     * @param commandToBuild: user-defined command name.
     * @param inputCommandList: remaining list of the user-inputed command string.
     * @return Node for the desired user-defined command.
     * @throws ClassNotFoundException
     */
    private Node handleCommand(String commandToBuild, List<String> inputCommandList) throws ClassNotFoundException {
        Node node;
        if (commandDict.contains(commandToBuild)) {
            node = commandDict.getCommandFor(commandToBuild);
            node.setNumChildrenNeeded(commandDict.getNumArgsForkey(commandToBuild));
            addChildrenToNode(node, inputCommandList);
        } else {
            node = new Command(commandToBuild);
        }
        return node;
    }

    /**
     * Checks if the given command requires a turtle.
     * @param name: name of command.
     * @return true if it requires a turtle; false o.w.
     */
    private boolean isTurtleCommand(String name) {
    	return Arrays.asList(myTurtleCommands.getString(TURTLE_COMMANDS).split(",")).contains(name);
    }
    
    /**
     * Checks if the given command requires changing the display.
     * @param name: name of command.
     * @return true if it requires changing the display; false o.w.
     */
    private boolean isDisplayCommand(String name) {
    	return Arrays.asList(myDisplayCommands.getString(DISPLAY_COMMANDS).split(",")).contains(name);
    }
    
    /**
     * Creates an object of the type specific to the command function being called.
     * @param name: name of command.
     * @return node object for the command.
     * @throws ClassNotFoundException
     */
    private Node getFunctionObject(String name) throws ClassNotFoundException {
        Class<?> className = Class.forName(MODEL + name);
        try {
            if (isTurtleCommand(name)) {
            	TurtleNode myNode = (TurtleNode) className.newInstance();
                myNode.setTurtleList(myCurTurtles);
                myNode.setNumChildrenNeeded(Integer.parseInt(myNumChildrenPerCommand.getString(name)));
                return myNode;
            } else if (isDisplayCommand(name)) {
            	DisplayNode myNode = (DisplayNode) className.newInstance();
            	myNode.setCanvas(myCanvas);
                myNode.setNumChildrenNeeded(Integer.parseInt(myNumChildrenPerCommand.getString(name)));
                return myNode;
            } else {
            	Node myNode = (Node) className.newInstance();
                myNode.setNumChildrenNeeded(Integer.parseInt(myNumChildrenPerCommand.getString(name)));
                return myNode;
            }
        } catch (Exception e) {
            //error
        }
        return null;
    }
    
    /**
     * Add children to a node based on the number of children required for the command.
     * @param node: node to add children to.
     * @param inputCommandList: remaining list of user input.
     * @throws ClassNotFoundException
     */
    private void addChildrenToNode(Node node, List<String> inputCommandList) throws ClassNotFoundException {
        for (int i = 0; i < node.getNumChildrenNeeded(); i++) {
            Node childNode = createClass(inputCommandList.get(0), inputCommandList);
            if (childNode != null) {
                node.addChild(childNode);
            }
        }
    }

    /**
     * Sets the current language.
     * @param language: language to set to.
     */
    public void setCurrentLanguage(String language){
        currentLanguage = language;
    }

    /**
     * Checks for a match between text and a regex pattern.
     */
    private boolean match(String text, Pattern regex) {
        return regex.matcher(text).matches();
    }

    /**
     * Gets the variable dictionary for this workspace.
     * @return variable dictionary for this workspace.
     */
    public VariableDictionary getVariables() {
        return varDict;
    }
}
