package xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import controller.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.CommandDictionary;
import model.VariableDictionary;

import java.io.File;

/**
 * Created by blakekaplan on 3/4/16.
 */
public class XMLSaver {

	private static final String XML_STYLE = "{http://xml.apache.org/xslt}indent-amount";
	private static final String CONFIG = "Config";
	private static final String BACKGROUND_COLOR = "BackgroundColor";
	private static final String PEN_COLOR = "PenColor";
	private static final String VARIABLES = "Variables";
	private static final String COMMANDS = "Commands";
	private static final String COMMAND = "Command";
	private static final String NAME = "Name";
	private static final String PROCEDURE = "Procedure";
	private static final String NUMBER_OF_ARGUMENTS = "NumberOfArguments";
	private static final String TURTLE_IMAGE = "TurtleImage";
	private static final String SAVING_ERROR = "SavingError";
	private static final String SLOGO_STATE = "SLogoState";
	private static final String YES = "yes";
	private static final String XML = "xml";
	private static final String FOUR = "4";
	private DocumentBuilderFactory myFactory;
	private DocumentBuilder myBuilder;
	private Document myDocument;
	private CommandDictionary myCommandDict;
	private VariableDictionary myVarDict;
	private Controller myController;

	public XMLSaver(CommandDictionary comDict, VariableDictionary varDict, Controller controller) {

		myController = controller;

		try {
			myFactory = DocumentBuilderFactory.newInstance();
			myBuilder = myFactory.newDocumentBuilder();
			myCommandDict = comDict;
			myVarDict = varDict;
		} catch (ParserConfigurationException e) {
			System.out.println("Document Builder Error");
		}
	}

	/**
	 * Accumulates all necessary XML file information and organizes it to be
	 * converted to an XML file
	 *
	 * @param backgroundColor
	 *            The string representation of the workspace background color
	 * @param penColor
	 *            The string representation of the workspace pen color
	 * @param turtleImage
	 *            The name of the turtle image
	 * @param file
	 *            The file to be written
	 */
	public void generateFile(String backgroundColor, String penColor, String turtleImage, File file) {
		myDocument = myBuilder.newDocument();
		Element myRoot = myDocument.createElement(SLOGO_STATE);
		myDocument.appendChild(myRoot);
		myRoot.appendChild(getConfig(backgroundColor, penColor, turtleImage));
		myRoot.appendChild(getVariables());
		myRoot.appendChild(getCommands());
		createFile(file);
	}

	/**
	 * Creates the XML file using the DOM elements created in generateFile
	 *
	 * @param file
	 *            The file to be written
	 */
	public void createFile(File file) {
		TransformerFactory myTransformerFactory = TransformerFactory.newInstance();
		Transformer myTransformer;
		try {
			myTransformer = myTransformerFactory.newTransformer();
			myTransformer.setOutputProperty(OutputKeys.INDENT, YES);
			myTransformer.setOutputProperty(OutputKeys.METHOD, XML);
			myTransformer.setOutputProperty(XML_STYLE, FOUR);
			DOMSource mySource = new DOMSource(myDocument);
			StreamResult myResult = new StreamResult(file);
			myTransformer.transform(mySource, myResult);
		} catch (Exception e) {
			myController.displayAlert(SAVING_ERROR);
		}
	}

	/**
	 * Creates a DOM element for the configuration information based on the
	 * provided data
	 *
	 * @param backgroundColor
	 *            The workspace background color
	 * @param penColor
	 *            The workspace pen color
	 * @param turtleImage
	 *            The workspace turtle image
	 * @return A DOM element containing the provided data
	 */
	private Element getConfig(String backgroundColor, String penColor, String turtleImage) {
		Element configElement = myDocument.createElement(CONFIG);
		configElement.appendChild(makeElement(BACKGROUND_COLOR, backgroundColor));
		configElement.appendChild(makeElement(PEN_COLOR, penColor));
		configElement.appendChild(makeElement(TURTLE_IMAGE, turtleImage));
		return configElement;
	}

	/**
	 * Creates a DOM element for the user defined variables
	 *
	 * @return A DOM element containing the user defined variables
	 */
	private Element getVariables() {
		Element variablesElement = myDocument.createElement(VARIABLES);
		for (String key : myVarDict.getKeySet()) {
			variablesElement.appendChild(makeElement(key, "" + (int) myVarDict.getNodeFor(key)));
		}
		return variablesElement;
	}

	/**
	 * Creates a DOM element containing the user defined commands
	 * 
	 * @return A DOM element containing the user defined commands
	 */
	private Element getCommands() {
		Element commandElement = myDocument.createElement(COMMANDS);
		for (String key : myCommandDict.getCommandTextKeySet()) {
			commandElement.appendChild(makeCommandElement(key));
		}
		return commandElement;
	}

	/**
	 * Given a specific command name, creates a DOM entry for the specific user
	 * defined command
	 * 
	 * @param key
	 *            The name of the command
	 * @return A DOM entry containing the data about the specific command
	 */
	private Element makeCommandElement(String key) {
		Element commandElement = myDocument.createElement(COMMAND);
		commandElement.appendChild(makeElement(NAME, key));
		commandElement.appendChild(makeElement(PROCEDURE, myCommandDict.getCommandTextForKey(key)));
		commandElement.appendChild(makeElement(NUMBER_OF_ARGUMENTS, "" + myCommandDict.getNumArgsForkey(key)));
		return commandElement;
	}

	/**
	 * Makes a DOM entry for a specific nodeName and data to go in a text entry
	 * 
	 * @param nodeName
	 *            The name of the DOM entry
	 * @param data
	 *            The string to be containing the text node
	 * @return A DOM entry containing the information
	 */
	private Element makeElement(String nodeName, String data) {
		Element myElement = myDocument.createElement(nodeName);
		myElement.appendChild(myDocument.createTextNode(data));
		return myElement;
	}
}
