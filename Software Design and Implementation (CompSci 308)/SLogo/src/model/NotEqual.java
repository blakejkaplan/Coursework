package model;

/**
 * NotEqual function.
 */
public class NotEqual extends BooleanNode {

    private static final String NOTEQUAL = "NotEqual ";
    private static final int EXPR = 0;

    /**
     * If the any of the child expressions are not equal to the rest, returns 1; else 0.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
    @Override
    protected boolean checkCondition(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return countNumEqual(getChildren().get(EXPR).interpret(commandDict, varDict), commandDict, varDict) < getChildren().size();
	}

    /**
	 * Returns the class name and its children.
	 */
    public String toString() {
        return NOTEQUAL + childrenToString();
    }
}
