package model;

/**
 * Right function.
 */
public class Right extends TurtleNode {

	private static final String RIGHT = "Right ";

	/**
     * Rotates the turtle CW by the given number of degrees.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		turtle.setDirection(turtle.getDirection() + combineChildren(0, commandDict, varDict));
		return combineChildren(0, commandDict, varDict);
	}

	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return RIGHT + childrenToString();
	}	
}
