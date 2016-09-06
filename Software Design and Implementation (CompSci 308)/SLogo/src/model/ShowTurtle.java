package model;

/**
 * ShowTurtle function.
 */
public class ShowTurtle extends TurtleNode {

	private static final String SHOWTURTLE = "ShowTurtle ";
	
	/**
     * Sets the turtle to visible and returns 1.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		turtle.setVisible(true);
		return 1;
	}

	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return SHOWTURTLE;
	}
}
