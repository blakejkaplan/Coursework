package model;

/**
 * LessThan function.
 */
public class LessThan extends BooleanNode {

    private static final String LESS = "LessThan ";
    private static final int EXPR1 = 0;
    private static final int EXPR2 = 1;

    /**
     * If expr1 is less than expr2, returns 1; else returns 0.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    protected boolean checkCondition(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
   		return getChildren().get(EXPR1).interpret(commandDict, varDict) < getChildren().get(EXPR2).interpret(commandDict, varDict);
   	}

    /**
	 * Returns the class name and its children.
	 */
    public String toString() {
        return LESS + childrenToString();
    }

}
