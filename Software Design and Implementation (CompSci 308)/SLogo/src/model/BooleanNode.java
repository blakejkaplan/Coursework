package model;

import java.util.List;
import java.util.function.Predicate;

public abstract class BooleanNode extends Node {

	/**
	 * Returns 1 if given condition is satisfied; 0 o.w.
	 */
	@Override
	public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
		if (checkCondition(commandDict, varDict)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Counts the number of children nodes that evaluate to a non-zero number.
	 * @param commandDict: command dictionary for current workspace.
	 * @param varDict: variable dictionary for current workspace.
	 * @return: number of child nodes that evaluate to true.
	 * @throws ClassNotFoundException
	 */
	protected int countNumTrue(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
		Predicate<Double> trueCondition = val -> val != 0;
		return countNumMatchingCondition(trueCondition, commandDict, varDict);
	}

	/**
	 * Counts the number of children nodes that equal a given number.
	 * @param valToMatch: value that the children must equal to be counted.
	 * @param commandDict: command dictionary for current workspace.
	 * @param varDict: variable dictionary for current workspace.
	 * @return: number of child nodes that equal the given value.
	 * @throws ClassNotFoundException
	 */
	protected int countNumEqual(double valToMatch, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
		Predicate<Double> equalCondition = val -> val == valToMatch;
		return countNumMatchingCondition(equalCondition, commandDict, varDict);
	}

	/**
	 * Counts the number of children that satisfy a given predicate.
	 * @param condition: condition to satisfy.
	 * @param commandDict: command dictionary for current workspace.
	 * @param varDict: variable dictionary for current workspace.
	 * @return the number of children that satisfy the given predicate.
	 * @throws ClassNotFoundException
	 */
	private int countNumMatchingCondition(Predicate<Double> condition, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
		List<IFunctions> children = getChildren();
		int numMatching = 0;
		for (int i = 0; i < children.size(); i++) {
			if (condition.test(children.get(i).interpret(commandDict, varDict))) {
				numMatching++;
			}
		}
		return numMatching;
	}

	/**
	 * Abstract method for all boolean nodes that checks whether or not a condition specific to that function is satisfied.
	 */
	protected abstract boolean checkCondition(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException;
}
