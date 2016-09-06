package model;

/**
 * Not function.
 */
public class Not extends BooleanNode {

    private static final String NOT = "Not ";
    private static final int EXPR = 0;

    /**
     * If the given expression is false, return 1; else return 0.
     *  @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    protected boolean checkCondition(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
   		return getChildren().get(EXPR).interpret(commandDict, varDict) == 0;
   	}

    /**
	 * Returns the class name and its children.
	 */
    public String toString() {
        return NOT + childrenToString();
    }

}
