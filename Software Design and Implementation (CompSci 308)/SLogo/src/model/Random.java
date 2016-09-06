package model;

/**
 * Random number function.
 */
public class Random extends Node {

    private static final String RANDOM = "Random ";
    private static final int MAX = 0;

    /**
     * Returns a random value between 0 and the given max.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return Math.random() * getChildren().get(MAX).interpret(commandDict, varDict);
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return RANDOM + childrenToString();
    }
}
