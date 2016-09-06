package model;

/**
 * XCoordinate function.
 */
public class XCoordinate extends TurtleNode {

	private static final String XCOR = "XCoordinate ";

	/**
     * Returns the turtle's current x-coordinate.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return turtle.getCurX();
	}

	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return XCOR;
	}
}
