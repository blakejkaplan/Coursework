package model;

import java.util.ArrayList;
import java.util.List;

public class AskWith extends TurtleNode {

	private static final String ASKWITH = "AskWith ";
	private static final int CONDITION = 0;
	
	/**
	 * Activates the turtles that match the given condition and directs them to carry out the given commands, then restores the
	 * original set of active turtles.
	 */
	@Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		List<Double> activeTurtleIDs = checkTurtlesForCondition(getTurtles(), commandDict, varDict);		
		return applyToTurtlesInList(activeTurtleIDs, getActiveTurtles(), commandDict, varDict);

	}

	/**
	 * Checks a list of turtles for the ones that satisfy a given condition.
	 * @param allTurtles: list of existing turtles.
	 * @param commandDict: command dictionary of current workspace.
	 * @param varDict: variable dictionary of current workspace.
	 * @return list of IDs of the turtles who satisfy the given condition.
	 * @throws ClassNotFoundException
	 * @throws NullPointerException
	 * @throws IndexOutOfBoundsException
	 */
	private List<Double> checkTurtlesForCondition(List<Turtle> allTurtles, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
		List<Double> turtleIDs = new ArrayList<>();
		inactivateAllTurtles();
		for (int i = 0; i < allTurtles.size(); i++) {
			allTurtles.get(i).setActive(true);
			allTurtles.get(i).changeCurrentTurtleStatus(true);
			if (getChildren().get(CONDITION).interpret(commandDict, varDict) == 1) {
				turtleIDs.add(allTurtles.get(i).getID());
			}
			allTurtles.get(i).setActive(false);
			allTurtles.get(i).changeCurrentTurtleStatus(false);
		}
		return turtleIDs;
	}

	/**
	 * Not used for this class.
	 */
	@Override
    protected double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict)
            throws ClassNotFoundException {
		return 0;
	}
	
	/**
	 * Returns the class name and its children.
	 */
	@Override
	public String toString() {
		return ASKWITH + childrenToString();
	}
}