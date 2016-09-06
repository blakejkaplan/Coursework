package model;

/**
 * Product function.
 */
public class Product extends Node {

    private static final String PRODUCT = "Product ";

	/**
     * Returns the product of all child expressions.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return combineChildren(1, commandDict, varDict);

    }
    
    /**
     * Combines the child node's value by multiplying it with the current value.
     */
    @Override
    protected double addChildValue(double val, IFunctions child, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return val * child.interpret(commandDict, varDict);
    }
    
    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return PRODUCT + childrenToString();
    }
}
