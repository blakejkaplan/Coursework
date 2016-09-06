package model;

/**
 * Pi function.
 */
public class Pi extends Node {

    private static final String PI = "Pi ";

    /**
     * Returns the value of pi.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return Math.PI;
    }

    /**
	 * Returns the class name.
	 */
    @Override
    public String toString() {
        return PI;
    }
}
