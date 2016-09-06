package model;

import java.util.List;

/**
 * Dotimes function.
 */
public class DoTimes extends Node {

    private static final String DOTIMES = "DoTimes ";
    private static final int VARIABLE_AND_LIMIT = 0;
    private static final int COMMANDS = 1;
    private static final int VARIABLE = 0;
    private static final int LIMIT = 1;

    /**
     * Executes the given command the given number of times.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        List<IFunctions> children = getChildren();
        IFunctions iterVar = children.get(VARIABLE_AND_LIMIT);
        String var = iterVar.getChildren().get(VARIABLE).toString();
        double limit = iterVar.getChildren().get(LIMIT).interpret(commandDict, varDict);
        double ret = 0;

        for (double i = 1; i <= limit; i++) {
            varDict.makeVariable(var, i);
            ret = children.get(COMMANDS).interpret(commandDict, varDict);
        }

        return ret;
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return DOTIMES + childrenToString();
    }

}
