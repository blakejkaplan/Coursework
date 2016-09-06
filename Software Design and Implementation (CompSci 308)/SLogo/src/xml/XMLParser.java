package xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by blakekaplan on 3/4/16.
 */
public class XMLParser {

	private static final String CONFIG = "Config";
	private static final String DELIMITER = "-";
	private static final String MAKE = "make ";
	private static final String COMMANDS = "Commands";
	private static final String VARIABLES = "Variables";
	private static final int IMAGE_INDEX = 2;
	private static final int PEN_COLOR_INDEX = 1;
	private static final int BACKGROUND_COLOR_INDEX = 0;
	private DocumentBuilderFactory myFactory;
	private DocumentBuilder myBuilder;

	private String backgroundColor;
	private String penColor;
	private String turtleImage;
	private List<String> extractedData;
	private List<String> commandStrings;
	private List<String> variableStrings;

	/**
	 * Given a file, extracts information from the DOM elements to use them for
	 * SLogo workspace configuration
	 * 
	 * @param myFile
	 *            The XML file containing the workspace data
	 * @throws IOException
	 *             XML reading/parsing error
	 * @throws ParserConfigurationException
	 *             XML reading/parsing error
	 * @throws SAXException
	 *             XML reading/parsing error
	 */
	public void parse(File myFile) throws IOException, ParserConfigurationException, SAXException {
		myFactory = DocumentBuilderFactory.newInstance();
		myBuilder = myFactory.newDocumentBuilder();
		Document myDocument = myBuilder.parse(myFile);
		myDocument.getDocumentElement().normalize();
		NodeList categories = myDocument.getDocumentElement().getChildNodes();
		for (int i = 0; i < categories.getLength(); i++) {
			handleDocumentNode(categories.item(i));
		}
        getCommandStrings();
        getVariableStrings();
	}

	/**
	 * Given a Node from the XML document, parses it properly
	 * 
	 * @param entry
	 *            The DOM node to be parsed
	 */
	private void handleDocumentNode(Node entry) {
		if (entry instanceof Element) {
			Element entryElement = (Element) entry;
			switch (entryElement.getNodeName()) {
			case CONFIG:
				parseConfig(entryElement);
				break;
			case VARIABLES:
				parseVariables(entryElement);
				break;
			case COMMANDS:
				parseCommands(entryElement);
				break;
			}
		}
	}

	/**
	 * Parses the information contained in a config DOM
	 * 
	 * @param configElement
	 *            The provided config DOM
	 */
	private void parseConfig(Element configElement) {
		List<String> configData = extract(configElement);
		backgroundColor = getTextForEntry(configData.get(BACKGROUND_COLOR_INDEX));
		penColor = getTextForEntry(configData.get(PEN_COLOR_INDEX));
		turtleImage = getTextForEntry(configData.get(IMAGE_INDEX));
	}

	/**
	 * Adds the variable string to the variableStrings list
	 * 
	 * @param variableElement
	 *            The provided variables DOM
	 */
	private void parseVariables(Element variableElement) {
		List<String> variableData = extract(variableElement);
        variableStrings = new ArrayList<>();
		for (String var : variableData) {
			variableStrings.add(MAKE + getNameForEntry(var) + " " + getTextForEntry(var));
		}
	}

	/**
	 * Creates a NodeList from a DOM element and applies the provided function
	 * to each Node in the NodeList
	 * 
	 * @param myElement
	 *            The provided DOM element
	 * @param myFunc
	 *            The consumer lambda function to be applied to each element
	 */
	private void loopThroughNodelist(Element myElement, Consumer<Element> myFunc) {
		NodeList dataList = myElement.getChildNodes();
		for (int i = 0; i < dataList.getLength(); i++) {
			Node dataNode = dataList.item(i);
			if (dataNode instanceof Element) {
				Element dataElement = (Element) dataNode;
				myFunc.accept(dataElement);
			}
		}
	}

	/**
	 * Creates a list of Strings from the children of a provided DOM element
	 * 
	 * @param data
	 *            The provided DOM element
	 * @return A list of strings extracted from the children of the provided DOM
	 *         element
	 */
	private List<String> extract(Element data) {
		extractedData = new ArrayList<>();
		loopThroughNodelist(data, element -> addToExtractedData(element));
		return extractedData;
	}

	/**
	 * Adds data to the extractedData list
	 * 
	 * @param myElement
	 *            A provided DOM element
	 */
	private void addToExtractedData(Element myElement) {
		extractedData.add(myElement.getNodeName() + DELIMITER + myElement.getTextContent());
	}

	/**
	 * Parses the information contained in the commands DOM
	 * 
	 * @param commandsElement
	 *            The provided commands DOM
	 */
	private void parseCommands(Element commandsElement) {
        commandStrings = new ArrayList<>();
		loopThroughNodelist(commandsElement, element -> parseMethodFromElement(element));
	}

	/**
	 * Adds the command string to the commandString list
	 * 
	 * @param myElement
	 *            A provided command DOM
	 */
	private void parseMethodFromElement(Element myElement) {
		List<String> data = extract(myElement);
		commandStrings.add(getTextForEntry(data.get(1)));
	}

	/**
	 * Provided with a string, splits it by a standard delimiter and extracts
	 * the second part of the string
	 * 
	 * @param entry
	 *            A provided string
	 * @return The second part of the split string
	 */
	private String getTextForEntry(String entry) {
		String[] splitEntry = entry.split(DELIMITER);
		return splitEntry[1];
	}

	/**
	 * Provided with a string, spltis it by a standard delimiter and extracts
	 * the first part of the string
	 * 
	 * @param entry
	 *            A provided string
	 * @return The first part of the split string
	 */
	private String getNameForEntry(String entry) {
		String[] splitEntry = entry.split(DELIMITER);
		return splitEntry[0];
	}

	/**
	 * Provides the parsed background color
	 * 
	 * @return The parsed background color in string form
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Provides the parsed pen color
	 * 
	 * @return The parsed pen color in string form
	 */
	public String getPenColor() {
		return penColor;
	}

	/**
	 * Provides the parsed turtle image
	 * 
	 * @return The parsed turtle image name in string form
	 */
	public String getTurtleImage() {
		return turtleImage;
	}

	/**
	 * Provides a list of the commands to be converted to command trees in the
	 * controller
	 * 
	 * @return A list of command strings
	 */
	public List<String> getCommandStrings() {
        System.out.println(commandStrings);
		return commandStrings;
	}

	/**
	 * Provides a list of the variables to be converted to a usable variable
	 * object in the controller
	 * 
	 * @return A list of variable strings
	 */
	public List<String> getVariableStrings() {
        System.out.println(variableStrings);
		return variableStrings;
	}

}