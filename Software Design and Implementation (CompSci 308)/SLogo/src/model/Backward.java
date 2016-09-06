package model;

/**
 * Backward function.
 */
public class Backward extends TurtleNode {

	private static final String BACKWARD = "Backward ";

	/**
     * Moves the turtle backwards a given distance and returns the distance moved.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
		double dist = combineChildren(0, commandDict, varDict);
		return turtle.move(-dist);
	}
	
	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return BACKWARD + childrenToString();
	}
}