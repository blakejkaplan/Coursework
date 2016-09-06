package model;

/**
 * And function.
 */
public class And extends BooleanNode {

    private static final String AND = "And ";

    /**
     * If all child expressions are true, returns true.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     * @throws IndexOutOfBoundsException
     * @throws NullPointerException 
     * @throws ClassNotFoundException 
     */
    @Override
	protected boolean checkCondition(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
		return countNumTrue(commandDict, varDict) == getChildren().size();
	}

    /**
	 * Returns the class name and its children.
	 */
    public String toString() {
        return AND + childrenToString();
    }


}
