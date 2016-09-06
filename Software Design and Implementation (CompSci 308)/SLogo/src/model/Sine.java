package model;

/**
 * Sine function.
 */
public class Sine extends Node {

    private static final String SINE = "Sine ";
    private static final int DEGREES = 0;

    /**
     * Returns the sine of the expression, where the expression is given in degrees.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return Math.sin(Math.toRadians(getChildren().get(DEGREES).interpret(commandDict, varDict)));
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return SINE + childrenToString();
    }
}
