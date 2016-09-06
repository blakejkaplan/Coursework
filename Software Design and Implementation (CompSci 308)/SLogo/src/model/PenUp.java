package model;

/**
 * PenUp function.
 */
public class PenUp extends TurtleNode {

	private static final String PENUP = "PenUp ";

	/**
     * Lifts pen up for current turtle so trail will no longer show.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		turtle.setPenUp(true);
		return 0;
	}

	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return PENUP;
	}



}
