package model;

/**
 * Equal function.
 */
public class Equal extends BooleanNode {
	
	private static final String EQUAL = "Equal ";
	private static final int EXPR = 0;
	
	/**
     * Returns 1 if the all expressions are equal; 0 otherwise.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected boolean checkCondition(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return countNumEqual(getChildren().get(EXPR).interpret(commandDict, varDict), commandDict, varDict) == getChildren().size();
	}
	
	/**
	 * Returns the class name and its children.
	 */
	public String toString() {
		return EQUAL + childrenToString();
	}
}
