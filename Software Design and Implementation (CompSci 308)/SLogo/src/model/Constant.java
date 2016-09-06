package model;

/**
 * Constant object.
 */
public class Constant extends Node {

    private int val;

    /**
     * Sets the value of this constant.
     * @param val: value of the constant.
     */
    public Constant(int val) {
        this.val = val;
    }

    /**
     * Returns the value of this constant.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return val;
    }

    /**
	 * Returns the value of this constant.
	 */
    @Override
    public String toString() {
        return Integer.toString(val);
    }

}
