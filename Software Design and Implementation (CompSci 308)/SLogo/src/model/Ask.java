package model;

import java.util.List;

public class Ask extends TurtleNode {

	private static final String ASK = "Ask ";
	private static final int TURTLE_IDS = 0;
	
	/**
	 * Activates the turtles with the specified IDs and directs them to carry out the given commands, then restores the
	 * original set of active turtles.
	 */
	@Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		List<Double> turtleIDs = createListFromCommandList((CommandList) getChildren().get(TURTLE_IDS), commandDict, varDict);
		return applyToTurtlesInList(turtleIDs, getActiveTurtles(), commandDict, varDict);
	}

	/**
	 * Not used for this function.
	 */
	@Override
    protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return 0;
	}
	
	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return ASK + childrenToString();
	}
}
