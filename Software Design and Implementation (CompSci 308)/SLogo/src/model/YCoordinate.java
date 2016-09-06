package model;

/**
 * YCoordinate function.
 */
public class YCoordinate extends TurtleNode {

	private static final String YCOR = "YCoordinate ";

	/**
     * Returns the turtle's current y-coordinate.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return turtle.getCurY();
	}

	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return YCOR;
	}


}
