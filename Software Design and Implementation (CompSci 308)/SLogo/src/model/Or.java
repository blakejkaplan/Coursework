package model;

/**
 * Or function.
 */
public class Or extends BooleanNode {

	private static final String OR = "Or ";
	
	/**
     * If any of the child expressions are true, returns 1; else 0.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
	@Override
	protected boolean checkCondition(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return countNumTrue(commandDict, varDict) > 0;
	}
	
	/**
	 * Returns the class name and its children.
	 */
	public String toString() {
		return OR + childrenToString();
	}

	
}
