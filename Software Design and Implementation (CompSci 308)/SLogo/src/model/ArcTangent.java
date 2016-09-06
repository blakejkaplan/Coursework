package model;

/**
 * ArcTangent function.
 */
public class ArcTangent extends Node {

    private static final String ARC_TANGENT = "ArcTangent ";
    private static final int DEGREES = 0;
    
    /**
     * Returns the arctangent of the expression, where the expression is given in degrees.
     * @param commandDict: command dictionary for current workspace.
     * @param varDict: variable dictionary for current workspace.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return Math.atan(Math.toRadians(getChildren().get(DEGREES).interpret(commandDict, varDict)));
    }

    /**
	 * Returns the class name and its children.
	 */
    @Override
    public String toString() {
        return ARC_TANGENT + childrenToString();
    }
}
