package model;

import java.util.Collections;
import java.util.List;

public class Tell extends TurtleNode {

	private static final String TELL = "Tell ";
	private static final int TURTLE_IDS = 0;
	
	/**
	 * for tell [ n ], sets turtles #1 - n to active and creates them if they don't already exist. 
	 */
	@Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		List<Double> turtleIDs = createListFromCommandList((CommandList) getChildren().get(TURTLE_IDS), commandDict, varDict);
		activateTurtles(Collections.max(turtleIDs));	
		return turtleIDs.get(turtleIDs.size() - 1);
	}

	/**
	 * Not used for this class.
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
		return TELL + childrenToString();
	}
}
