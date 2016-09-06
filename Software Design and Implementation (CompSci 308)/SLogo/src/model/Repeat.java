package model;

import java.util.List;

/**
 * Repeat function.
 */
public class Repeat extends Node {

    private static final String REPEAT = "Repeat ";
    private static final int EXPR = 0;
    private static final int COMMANDS = 1;
    private static final String REPCOUNT_VARIABLE = ":repcount";

    /**
     * Repeats the given commands a given number of times.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        List<IFunctions> children = getChildren();
        double numIter = children.get(EXPR).interpret(commandDict, varDict);
        double ret = 0;

        for (int i = 0; i < numIter; i++) {
            varDict.makeVariable(REPCOUNT_VARIABLE, i);
            ret = children.get(COMMANDS).interpret(commandDict, varDict);
        }

        return ret;
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return REPEAT + childrenToString();
    }


}
