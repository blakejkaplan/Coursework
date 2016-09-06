package model;

/**
 * Difference function.
 */
public class Difference extends Node {

    private static final String DIFFERENCE = "Difference ";
    private static final int START = 0;

    /**
     * Returns the difference between the first child expression and the remaining child expressions.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return combineChildren(getChildren().get(START).interpret(commandDict, varDict) * 2, commandDict, varDict);
    }
    
    /**
     * Subtracts the child's value from the current value.
     */
    @Override
    protected double addChildValue(double val, IFunctions child, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return val - child.interpret(commandDict, varDict);
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return DIFFERENCE + childrenToString();
    }
}
