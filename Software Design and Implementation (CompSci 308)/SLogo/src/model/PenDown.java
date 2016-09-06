package model;

/**
 * PenDown function.
 */
public class PenDown extends TurtleNode {

	private static final String PENDOWN = "PenDown ";

	/**
     * Puts the turtle's pen down so trail will now show.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		turtle.setPenUp(false);
		return 1;
	}
	
	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return PENDOWN;
	}
}
