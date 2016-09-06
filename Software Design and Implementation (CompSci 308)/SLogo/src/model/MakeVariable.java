package model;

import java.util.List;

/**
 * MakeVariable function to implement Make.
 */

public class MakeVariable extends Node {

    private static final String MAKE = "MakeVariable ";
    private static final int VARIABLE_NAME = 0;
    private static final int EXPRESSION = 1;
    private String name;

    /**
     * Updates the entry in the variable dictionary to the new value.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        List<IFunctions> children = getChildren();
        String key = children.get(VARIABLE_NAME).toString();
        double value = children.get(EXPRESSION).interpret(commandDict, varDict);
        varDict.makeVariable(key, value);
        return value;
    }

    /**
     * Gets the variable name.
     * @return variable name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a variable's name to a new name.
     * @param newName: new name for variable.
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return MAKE + childrenToString();
    }
}
