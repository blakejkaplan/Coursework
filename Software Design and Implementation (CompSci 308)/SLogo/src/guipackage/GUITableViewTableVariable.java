package guipackage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Class to store data for each variable in the TableView.
 */
public class GUITableViewTableVariable {
	private final SimpleStringProperty variableName;
	private final SimpleDoubleProperty variableValue;
	
	public GUITableViewTableVariable (String name, Double value) {
		this.variableName = new SimpleStringProperty(name);
		this.variableValue = new SimpleDoubleProperty(value);
	}
	
	/**
	 * Returns variable name as a String.
	 * @return Variable name
	 */
	public String getVariableName() {
		return variableName.get();
	}
	
	/**
	 * Sets the variable name as specified String
	 * @param variableName
	 */
	public void setVariableName(String variableName) {
		this.variableName.set(variableName);
	}
	
	/**
	 * Returns variable value as a String.
	 * @return Variable value
	 */
	public double getVariableValue() {
		return variableValue.get();
	}

	/**
	 * Sets the variable value as specified String
	 * @param variableValue
	 */
	public void setVariableValue(Double variableValue) {
		this.variableValue.set(variableValue);
	}
	
}
