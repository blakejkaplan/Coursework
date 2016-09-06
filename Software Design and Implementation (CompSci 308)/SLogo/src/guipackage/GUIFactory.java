package guipackage;

import java.util.ResourceBundle;

import controller.Controller;

/**
 * Instantiate GUIObjects based on ResourceBundle key passed into createNewGUIObject(String key).
 */
public class GUIFactory {
	private ResourceBundle myResources;
	private GUICanvas canvas;
	private Controller myController;
	private GUICommandLine myCommandLine;
	private static final String PROMPT_TEXT = "PromptText"; 

	public GUIFactory(ResourceBundle myResources, Controller myController, GUICanvas canvas, GUICommandLine cLine){
		this.myResources = myResources;
		this.canvas = canvas; 
		this.myController = myController;
		this.myCommandLine = cLine;
	}

	/**
	 * Creates new GUIObject based on nodeTypeKey passed in. 
	 * @param nodeTypeKey
	 * @return Specified IGUIObject
	 */
	protected IGUIObject createNewGUIObject(String nodeTypeKey){
		String nodeType = myResources.getString(nodeTypeKey);
		switch(nodeType){
		case("AnimationControl"): return new GUIAnimationControl(myResources, canvas);
		case("SaveLoadButtons"): return new GUISaveLoad(myResources, myController, canvas, myCommandLine);
		case("ImageComboBox"): return new GUIComboBoxImages(canvas, myResources, myResources.getString(nodeTypeKey+PROMPT_TEXT));
		case("LanguageComboBox"): return new GUIComboBoxLanguages(canvas, myResources, myController,myResources.getString(nodeTypeKey+PROMPT_TEXT), myCommandLine);
		case("VariablesTableView"): return new GUITableView(myResources, myController.getVariableDictionary());
		case("UserCommandsComboBox"): return new GUIComboBoxUserHist(canvas, myResources, myController,myResources.getString(nodeTypeKey + PROMPT_TEXT), myCommandLine, myController.getCommandDictionary());
		case("PreviousCommandsComboBox"): return new GUIComboBoxCommandHist(canvas, myResources, myController, myResources.getString(nodeTypeKey+PROMPT_TEXT), myCommandLine);
		case("ShowHide"):return new GUITurtleStateToggle(myResources, canvas);
		}
		return null;
	}
}
