package model;

import java.util.List;

/**
 * SetPosition function.
 */
public class SetPosition extends TurtleNode {

    private static final String SETXY = "SetPosition ";
    private static final int X = 0;
    private static final int Y = 1;

    /**
     * Moves the turtle to the given position and returns the distance moved.
     *  @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
	protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		List<IFunctions> children = getChildren();
		double dist = turtle.calcDistance(children.get(X).interpret(commandDict, varDict), children.get(Y).interpret(commandDict, varDict));
		turtle.turnTowards(children.get(X).interpret(commandDict, varDict), children.get(Y).interpret(commandDict, varDict));
		turtle.move(dist);

		return dist;
	}

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return SETXY + childrenToString();
    }
}
