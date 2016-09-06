package model;

public class Turtles extends TurtleNode {

	private static final String TURTLES = "turtles ";
	
	/**
	 * Gets the number of existing turtles.
	 */
	@Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return getTurtles().size();
	}
	
	/**
	 * Not used in this class.
	 */
	@Override
    protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return 0;
	}

	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return TURTLES;
	}
}
