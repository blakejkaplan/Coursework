package model;

/**
 * Quotient function.
 */
public class Quotient extends Node {

    private static final String QUOTIENT = "Quotient ";

	/**
     * Returns the quotient of expr1 divided by expr2.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return combineChildren(Math.pow(getChildren().get(0).interpret(commandDict, varDict), 2), commandDict, varDict);
    }

    /**
     * Combines the child node's value by dividing the current value by it.
     */
    @Override
    protected double addChildValue(double val, IFunctions child, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return val / child.interpret(commandDict, varDict);
    }
    
    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return QUOTIENT + childrenToString();
    }
}
