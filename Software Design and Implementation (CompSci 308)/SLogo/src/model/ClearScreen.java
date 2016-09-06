package model;

/**
 * Clearscreen function.
 */
public class ClearScreen extends TurtleNode {

	private static final String CLEARSCREEN = "ClearScreen ";

	/**
     * Moves the turtle back to (0, 0) and erases its trails; returns the distance the turtle moved to get back to (0, 0).
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		double dist = turtle.moveToHome();
		turtle.resetTurtle();

		return dist;
	}

	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return CLEARSCREEN;
	}
}
