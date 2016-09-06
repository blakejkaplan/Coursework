package model;

/**
 * HideTurtle function.
 */
public class HideTurtle extends TurtleNode {

	private static final String HIDETURTLE = "HideTurtle ";

	/**
     * Sets the turtle to invisible.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		turtle.setVisible(false);
		return 0;
	}
	
	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return HIDETURTLE;
	}


}
