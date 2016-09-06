package model;

import java.util.List;

public class Define extends Node {

    public static final String DEFINE = "Define ";

    /**
     * Creates a placeholder entry in the command dictionary specifying the name and number of parameters
     * for a command that is to be made by the user.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        List<IFunctions> children = getChildren();
        Command myCommand = (Command) children.get(0);
        commandDict.createPlaceholderCommand(myCommand.toString(), (int) children.get(1).interpret(commandDict, varDict));
        return 1;
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return DEFINE + childrenToString();
    }
}
