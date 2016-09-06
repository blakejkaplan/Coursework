package model;

/**
 * Tangent function.
 */
public class Tangent extends Node {

    private static final String TANGENT = "Tangent ";
    private static final int DEGREES = 0;

    /**
     * Returns the tangent of the expression, where the expression is given in degrees.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return Math.tan(Math.toRadians(getChildren().get(DEGREES).interpret(commandDict, varDict)));
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return TANGENT + childrenToString();
    }
}
