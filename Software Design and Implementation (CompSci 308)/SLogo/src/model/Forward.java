package model;

/**
 * Forward function.
 */
public class Forward extends TurtleNode {

	private static final String FORWARD = "Forward ";

	/**
     * Moves the turtle forward the given distance.
	 * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict,
			VariableDictionary varDict)
            throws ClassNotFoundException {
		double dist = combineChildren(0, commandDict, varDict);
		return turtle.move(dist);
	}

	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return FORWARD + childrenToString();
	}
}