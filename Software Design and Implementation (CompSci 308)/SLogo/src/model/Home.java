package model;

/**
 * Home function.
 */
public class Home extends TurtleNode {

	private static final String HOME = "Home ";

	/**
     * Moves the turtle back to the origin and returns the distance moved.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return turtle.moveToHome();
	}

	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return HOME;
	}
}
