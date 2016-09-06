package model;

/**
 * Minus function.
 */
public class Minus extends Node {

    private static final String MINUS = "Minus ";
    private static final int EXPR = 0;

    /**
     * Negates the given expression.
     *  @param commandDict
     * @param varDict*/
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return -1 * getChildren().get(EXPR).interpret(commandDict, varDict);
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return MINUS + childrenToString();
    }
}
