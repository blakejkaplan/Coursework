package guipackage;

import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
/**
 * Returns help tab for given URL. 
 */
public class TabHelp {
	private String webURL;
	private String tabText;
	
	public TabHelp(String webURL, String tabText) {
		this.webURL = webURL;
		this.tabText = tabText;
	}
	
	/**
	 * Sets up all elements on Tab and returns the Tab
	 * @return
	 */
	protected Tab getTab(){
		Tab helpTab = new Tab();
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load(webURL);
		helpTab.setContent(browser);
		helpTab.setText(tabText);
		return helpTab;
	}
}
