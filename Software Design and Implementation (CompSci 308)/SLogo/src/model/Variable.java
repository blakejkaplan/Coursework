package model;

/**
 * Variable object.
 */
public class Variable extends Node {

    private String name;

    /**
     * Sets the name of the variable of interest.
     * @param name: name of the variable the user wants to get.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Returns the value for the given variable.
     *  @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return varDict.getNodeFor(name);
    }

    /**
     * Returns the string representing this variable.
     */
    @Override
    public String toString() {
        return name;
    }

}
