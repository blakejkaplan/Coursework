package model;

/**
 * GreaterThan function.
 */
public class GreaterThan extends BooleanNode {

    private static final String GREATER = "GreaterThan ";
    private static final int EXPR1 = 0;
    private static final int EXPR2 = 1;

    /**
     * Returns 1 if expr1 is greater than expr2; 0 otherwise.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    protected boolean checkCondition(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return getChildren().get(EXPR1).interpret(commandDict, varDict) > getChildren().get(EXPR2).interpret(commandDict, varDict);
	}

    /**
	 * Returns the class name and its children.
	 */
    public String toString() {
        return GREATER + childrenToString();
    }

}
