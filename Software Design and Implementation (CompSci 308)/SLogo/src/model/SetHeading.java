package model;

import java.util.List;

/**
 * SetHeading function.
 */
public class SetHeading extends TurtleNode {
	
	private static final String SETHEADING = "SetHeading ";
	private static final int DEGREES = 0;
	
	/**
     * Turns the turtle towards to the given degrees, where 0 is facing north and rotating CW is positive.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		List<IFunctions> children = getChildren();
		double curDir = turtle.getDirection();
		turtle.setDirection(children.get(DEGREES).interpret(commandDict, varDict));
		return children.get(DEGREES).interpret(commandDict, varDict) - curDir;
	}

	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return SETHEADING + childrenToString();
	}
}
