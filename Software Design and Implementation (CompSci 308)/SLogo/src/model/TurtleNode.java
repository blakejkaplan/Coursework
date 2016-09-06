package model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class TurtleNode extends Node {
    private List<Turtle> myTurtles;

    /**
     * Creates a TurtleNode and initializes the list of turtles as null.
     */
    public TurtleNode() {
    	super();
        myTurtles = null;
    }
    
    /**
     * Sets the list of turtles.
     * @param curTurtles: current list of turtles.
     */
    public void setTurtleList(List<Turtle> curTurtles) {
    	myTurtles = curTurtles;
    }
    
    /**
     * Gets this node's turtle.
     * @return the turtle assigned to this node.
     */
    protected List<Turtle> getActiveTurtles() {
    	List<Turtle> curTurtles = getTurtles();
        List<Turtle> activeTurtles = new ArrayList<>();
        for (int i = 0; i < curTurtles.size(); i++) {
        	if (curTurtles.get(i).isActive()) {
        		activeTurtles.add(curTurtles.get(i));
        	}
        }
        return activeTurtles;
    }
    
    /**
     * Creates a turtle with a given ID.
     * @param ID: new turtle's ID.
     */
    public void createTurtle(double ID) {
    	Turtle turtle = new Turtle(ID);
		turtle.setActive(true);
    	myTurtles.add(turtle);
    }
    
    /**
     * Gets the list of existing turtles.
     * @return list of existing turtles.
     */
    public List<Turtle> getTurtles() {
    	if (myTurtles != null) {
    		if (myTurtles.isEmpty()) {
    			createTurtle(1);
    		}
    		return myTurtles;
    	}
    	return null;
    }
    
    /**
     * Applies a command to each individual active turtle.
     * @param list: list of turtles to apply command to.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     * @return value that the last command evaluated to when applied to the last turtle.
     * @throws ClassNotFoundException
     */
    protected double applyToActiveTurtles(List<Turtle> list, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        double ret = 0;
    	for (int i = 0; i < list.size(); i++) {
    		list.get(i).changeCurrentTurtleStatus(true);
    		ret = applyToIndividualTurtle(list.get(i), commandDict, varDict);
    		list.get(i).changeCurrentTurtleStatus(false);
    	}
    	return ret;
    }
    
    /**
     * Default interpret for TurtleNodes is to apply the command to all active turtles.
     */
    @Override
    public double interpret(CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        return applyToActiveTurtles(getActiveTurtles(), commandDict, varDict);
    }
    
    /**
     * Apply command-specific operations to each individual turtle.
     * @param turtle: turtle to apply operations to.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     * @return value that the command evaluates to.
     * @throws ClassNotFoundException
     */
    protected abstract double applyToIndividualTurtle(Turtle turtle, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException;
    
    /**
     * Get a turtle based on ID.
     * @param ID: ID of turtle to get.
     * @return turtle with given ID.
     */
    protected Turtle getTurtleByID(double ID) {
    	for (int i = 0; i < myTurtles.size(); i++) {
    		if (myTurtles.get(i).getID() == ID) {
    			return myTurtles.get(i);
    		}
    	}
    	return null;
    }
    
    /**
     * Activates all turtles in a given list.
     * @param turtleIDs: IDs of turtles to activate.
     */
    protected void activateTurtlesInList(List<Double> turtleIDs) {
    	inactivateAllTurtles();
		for (int i = 0; i < turtleIDs.size(); i++) {
			Turtle turtle = getTurtleByID(turtleIDs.get(i));
			if (turtle != null) {
				turtle.changeCurrentTurtleStatus(true);
				turtle.setActive(true);
				turtle.changeCurrentTurtleStatus(false);
			} else {
				createTurtle(turtleIDs.get(i));
			}
		}
    }
    
    /**
     * Applies a function to all turtles in a given list, then restores the set of active turtles to their active state.
     * @param turtleIDs: IDs of turtles to apply the operation on.
     * @param origActiveTurtles: original set of active turtles.
     * @param commandDict: command dictionary of current workspace.
     * @param varDict: variable dictionary of current workspace.
     * @return value that the operation evaluates to for the last turtle.
     * @throws ClassNotFoundException
     */
    protected double applyToTurtlesInList(List<Double> turtleIDs, List<Turtle> origActiveTurtles, CommandDictionary commandDict, VariableDictionary varDict) throws ClassNotFoundException {
        activateTurtlesInList(turtleIDs);
		double ret = getChildren().get(1).interpret(commandDict, varDict);
		activateTurtlesInList(getTurtleIDs(origActiveTurtles));
		return ret;
    }
    
    /**
     * Gets the IDs of turtles in a list.
     * @param turtles: turtles whose IDs to get.
     * @return IDs of turtles in a list.
     */
    protected List<Double> getTurtleIDs(List<Turtle> turtles) {
    	List<Double> IDs = new ArrayList<>();
    	for (int i = 0; i < turtles.size(); i++) {
    		IDs.add(turtles.get(i).getID());
    	}
    	return IDs;
    }
    
    /**
     * Activates all turtles with IDs from 1-maxID
     * @param maxID: largest ID of the turtles to activate.
     */
    protected void activateTurtles(double maxID) {
    	List<Double> IDs = new ArrayList<>();
    	for (double i = 1; i <= maxID; i = i+1) {
    		IDs.add(i);
    	}
    	activateTurtlesInList(IDs);
    }
    
    /**
     * Deactivates all existing turtles.
     */
    protected void inactivateAllTurtles() {
    	for (int i = 0; i < myTurtles.size(); i++) {
			Turtle turtle = myTurtles.get(i);
			turtle.setActive(false);
		}
    }
    
    /**
     * Checks if a turtle satisfies a given predicate.
     * @param pred: predicate to satisfy.
     * @param turtle: turtle to check.
     * @return 1 if turtle satisfies the predicate; 0 o.w.
     */
    protected double checkTurtleProperty(Predicate<Turtle> pred, Turtle turtle) {
    	if (pred.test(turtle)) {
    		return 1;
    	} else {
    		return 0;
    	}
    }
}
