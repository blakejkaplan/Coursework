package model;

import java.util.function.Predicate;

/**
 * IsPenDown function.
 */
public class IsPenDown extends TurtleNode {

	private static final String PENDOWNP = "IsPenDown ";

	/**
     * If the turtle's pen is down, returns 1; else returns 0.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		Predicate<Turtle> pen = t -> !t.isPenUp();
		return checkTurtleProperty(pen, turtle);
	}

	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return PENDOWNP;
	}	
}
