package model;

import java.util.List;

public abstract class IfNode extends Node {
	private static final int EXPR = 0;
	private static final int TRUE_COMMANDS = 1;
    private static final int FALSE_COMMANDS = 2;
    private static final int TWO_CHILDREN = 2;

    /**
	 * Checks if a value is non-zero.
	 * @param val: value to check.
	 * @return true if value is non-zero; false o.w.
	 */
	protected boolean expressionIsTrue(double val) {
		return val != 0;
	}
	
	/**
	 * Executes if and if/else statements.
	 * @param numChildren: number of children the node has.
	 * @param commandDict: command dictionary for current workspace.
	 * @param varDict: variable dictionary for current workspace.
	 * @return value that the if or if/else statement evaluates to.
	 * @throws ClassNotFoundException
	 */
	protected double ifStatement(int numChildren, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
		List<IFunctions> children = getChildren();
		if (expressionIsTrue(children.get(EXPR).interpret(commandDict, varDict))) {
			return children.get(TRUE_COMMANDS).interpret(commandDict, varDict);
		}
		else {
			if (numChildren > TWO_CHILDREN) {
				return children.get(FALSE_COMMANDS).interpret(commandDict, varDict);
			} else {
				return 0;
			}
		}
	}
	
	/**
	 * Interprets the if or if/else statement.
	 */
	@Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return ifStatement(getChildren().size(), commandDict, varDict);
    }
}
