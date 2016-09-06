package model;

/**
 * Sum function.
 */
public class Sum extends Node{
	
	private static final String SUM = "Sum ";
	
	/**
     * Returns the sum of all child expressions.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
	@Override
	public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
		return combineChildren(0, commandDict, varDict);
	}
	
	/**
	 * Returns the class name and its children.
	 */
	public String toString() {
		return SUM + childrenToString();
	}
}
