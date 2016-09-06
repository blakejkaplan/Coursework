package model;

/**
 * NaturalLog function.
 */
public class NaturalLog extends Node {

    private static final String NATURAL_LOG = "NaturalLog ";
    private static final int EXPR = 0;

    /**
     * Evaluates the natural logarithm of the given expression.
     *  @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return Math.log(getChildren().get(EXPR).interpret(commandDict, varDict));
    }


    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return NATURAL_LOG + childrenToString();
    }
}
