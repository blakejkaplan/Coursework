package model;

/**
 * Heading function.
 */
public class Heading extends TurtleNode {

	private static final String HEADING = "Heading ";

	/**
     * Returns the turtle's current direction.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return turtle.getDirection();
	}

	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return HEADING;
	}



}
