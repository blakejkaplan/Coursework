package model;

import java.util.function.Predicate;

/**
 * IsShowing function.
 */
public class IsShowing extends TurtleNode {

	private static final String SHOWINGP = "IsShowing ";

	/**
     * Returns 1 if the turtle is showing on the canvas; 0 otherwise.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
	@Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		Predicate<Turtle> show = t -> t.showing();
		return checkTurtleProperty(show, turtle);
	}
	
	/**
	 * Returns the class name.
	 */
	@Override
	public String toString() {
		return SHOWINGP;
	}
}
