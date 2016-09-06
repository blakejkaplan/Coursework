package Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import Model.Cell;
import Model.Grid;
import Model.StandardCell;
import javafx.scene.paint.Color;

public abstract class Rules {

	private List<Cell> toBeUpdated = new ArrayList<Cell>();
	protected Map<String, Integer> myStatesCount;
	protected Map<String, Color> myStatesColors;

	public static final String DEFAULT_RULES_RESOURCE = "Rules/Rules";
	private ResourceBundle myRulesResources;
	private int MY_CELL_ROW;
	private int MY_CELL_COL;
	
	/**
	 * Helper method that applies the specified rules to each method in the grid
	 */
	public void applyRulesToGrid(Grid grid){
		for(int r = 0; r < grid.getNumRows(); r++){
			for(int c = 0; c < grid.getNumCols(); c++){
				applyRulesToCell(grid.getCell(r,c), grid);
			}
		}
	}
	
	/**
	 * Initialize the Grid with the Cells corresponding to this simulation.
	 * @param grid: Simulation grid.
	 * @param initialStates: String 2D array with the initial states of each cell.
	 */
	public void initGrid(Grid grid, String[][] initialStates) {
		for (int row = 0; row < grid.getNumRows(); row++) {
			for (int col = 0; col < grid.getNumCols(); col++) {
				Cell cell = createCell(initialStates[row][col], row, col);
				grid.addCellToGrid(row, col, cell);
				increaseStateCount(cell.getCurState());
			}
		}
	}
	
	/**
	 * Populates the state and colors information into two maps
	 */
	public void populateStatesInfo(){
		myStatesCount = new HashMap<String, Integer>();
		myStatesColors = new HashMap<String, Color>();
		myRulesResources = ResourceBundle.getBundle(DEFAULT_RULES_RESOURCE);
		String ruleName = toString().replace(" ", "");
		String[] states = myRulesResources.getString(ruleName + "States").split(",");
		String[] colors = myRulesResources.getString(ruleName + "Colors").split(",");
		for(int i = 0; i < states.length; i++){
			myStatesCount.put(states[i], 0);
			Color color = Color.web(colors[i]);
			myStatesColors.put(states[i], color);
		}
		MY_CELL_ROW = Integer.parseInt(myRulesResources.getString("MyCellRow"));
		MY_CELL_COL = Integer.parseInt(myRulesResources.getString("MyCellCol"));
	}
	
	/**
	 * Creates the type of Cell corresponding to the correct simulation.
	 * @param initialState: initial state of the Cell.
	 * @param row: row of the initial location of the Cell.
	 * @param col: column of the initial location of the Cell.
	 * @return
	 */
	protected Cell createCell(String initialState, int row, int col) {
		return new StandardCell(initialState, row, col);
	}
	
	/**
	 * Counts the number of neighbors of a certain state.
	 * @param neighborhood: Cell[][] containing neighboring cells to check.
	 * @param state: state of cells to count.
	 * @return number of neighbors of a certain state.
	 */
	protected int countSurroundingNeighborsOfType(Cell[][] neighborhood, String state) {
		int ret = 0;
		for (int row = 0; row < neighborhood.length; row++) {
			for (int col = 0; col < neighborhood[row].length; col++) {
				if (neighborhood[row][col] != null) {
					if (row != MY_CELL_ROW || col != MY_CELL_COL) {
						if (neighborhood[row][col].getCurState().equals(state)) {
							ret++;
						}
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Creates a default cell specific to the simulation.
	 * @param row: row of the cell.
	 * @param col: column of the cell.
	 * @return a cell of a default state specific to the simulation.
	 */
	public Cell createDefaultCell(int row, int col) {
		return createCell(getDefault(), row, col);
	}
	
	/**
	 * Returns the default state of the simulation.
	 * @return default state of the simulation.
	 */
	public abstract String getDefault();
	
	/**
	 * Rules for each simulation to be implemented by simulation-specific subclasses.
	 * @param cell: Cell to apply rules to.
	 * @param grid: Simulation grid.
	 */
	public abstract void applyRulesToCell(Cell cell, Grid grid);

	/**
	 * Switch cell1 and cell2's states, set their next location's for reference, and add to the list of cells to be updated.
	 * @param cell1: first cell to be swapped.
	 * @param cell2: second cell to be swapped.
	 */
	protected void switchCells(Cell cell1, Cell cell2) {
		int cell1Row = cell1.getCurRow();
		int cell1Col = cell1.getCurCol();
		String cell1State = cell1.getCurState();
		
		cell1.setNextLocation(cell2.getCurRow(), cell2.getCurCol());
		cell1.setNextState(cell2.getCurState());
		
		cell2.setNextLocation(cell1Row, cell1Col);
		cell2.setNextState(cell1State);
		
		addCellToBeUpdated(cell1);
		addCellToBeUpdated(cell2);
	}
	
	/**
	 * Gets the list of Cells that need to be updated this round of the Simulation.
	 * @return list of Cells to be updated.
	 */
	public List<Cell> getToBeUpdatedList() {
		return toBeUpdated;
	}
	
	/**
	 * Adds a Cell to the list of Cells that need to be updated this round of the Simulation.
	 * @param cell: Cell to be updated.
	 */
	public void addCellToBeUpdated(Cell cell) {
		if (!toBeUpdated.contains(cell)) {
			toBeUpdated.add(cell);
		}
	}
	
	/**
	 * Removes a Cell from the list of Cells that need to be updated this round of the Simulation.
	 * @param cell: Cell that no longer needs to be updated.
	 */
	public void removeCellToBeUpdated(Cell cell) {
		toBeUpdated.remove(cell);
	}
	
	/**
	 * Clears the "toBeUpdated" list.
	 */
	public void clearToBeUpdatedList(){
		toBeUpdated.clear();
	}
	
	/**
	 * Gets a map containing the number cells in each state.
	 * @return map mapping state to number of cells in that state.
	 */
	public Map<String, Integer> getMyStatesCount() {
		return myStatesCount;
	}

	/**
	 * Gets a map containing the color for each
	 * @return map mapping state to color for that state.
	 */
	public Map<String, Color> getMyStatesColors() {
		return myStatesColors;
	}
	
	/**
	 * Increases the count for the number of cells in a particular state by 1.
	 * @param state: state whose count to increase.
	 */
	public void increaseStateCount(String state) {
		if (!myStatesCount.containsKey(state)) {
			myStatesCount.put(state, 1);
		} else {
			myStatesCount.put(state, myStatesCount.get(state) + 1);
		}
	}
	
	/**
	 * Decreases the count for the number of cells in a particular state by 1.
	 * @param state: state whose count to decrease.
	 */
	public void decreaseStateCount(String state) {
		if (myStatesCount.containsKey(state)) {
			int num = myStatesCount.get(state);
			if (num > 0) {
				myStatesCount.put(state, num - 1);
			}
		}
	}

	/**
	 * Updates the state counts when a cell switches state.
	 * @param cell: cell that is switching states.
	 */
	public void updateStateCount(Cell cell) {
		if (cell.getNextState() != null) {
			decreaseStateCount(cell.getCurState());
			increaseStateCount(cell.getNextState());
		}
	}
	
	/**
	 * Checks if the cell is the last one in the grid.
	 * @param cell: cell to check.
	 * @param grid: simulation grid.
	 * @return true if it is the last cell; false otherwise.
	 */
	protected boolean isLastCellInGrid(Cell cell, Grid grid) {
		int offset = 1;
		if (grid.hasBeenResizedThisStep()) {
			offset = 2;
		}
		return (cell.getCurRow() == (grid.getNumRows() - offset)) && (cell.getCurCol() == (grid.getNumCols() - offset));
	}

	/**
	 * Generates a random integer between 0 and (max-1) for indexing into a list.
	 * @param max: size of list you're indexing into.
	 * @return an integer for the random index.
	 */
	protected int generateRandom(int max) {
		return (int) Math.round(Math.random() * (max-1));
	}
	
	/**
	 * Returns string name of the Simulation
	 */
	public abstract String toString();
	
	/**
	 * Retrieves a list of all the rule parameters
	 * @return
	 * An ArrayList of Strings containing the game parameters
	 */
	public abstract List<String> getParameters();
}
